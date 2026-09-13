package com.project.hostelms.service.impl;

import com.project.hostelms.CommonFuns;
import com.project.hostelms.LoginTypes;
import com.project.hostelms.dto.VisitorDTO;
import com.project.hostelms.entity.*;
import com.project.hostelms.repository.*;
import com.project.hostelms.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private VisitorRepository visitors;
    @Autowired
    private RoomRepository rooms;
    @Autowired
    private BlockRepository blocks;
    @Autowired
    private UsersRepository members;
    @Autowired
    private RegistrationRequestRepository regrequests;
    @Autowired
    private StudentRepository students;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<VisitorDTO> getVisitors() {
        List<VisitorDTO> vrequests = new ArrayList<>();
        for (Visitor v : visitors.findByStatus(1, Sort.by("id").ascending())) {
            VisitorDTO dto = new VisitorDTO();
            Room f = rooms.findById(v.getRoom());
            dto.setRoom(f.getRoomno() + "(" + blocks.findById(f.getBlock()).getName() + ")");
            dto.setVisitor(v.getVisitorname());
            dto.setPurpose(v.getPurpose());
            dto.setVisitingtime(v.getVisitingtime());
            dto.setUser(members.findById(v.getStudent()).getFullname());
            dto.setId(f.getId());
            vrequests.add(dto);
        }
        return vrequests;
    }

    @Override
    public void processRegistration(int id, int status) throws Exception {
        RegistrationRequest obj = regrequests.findById(id);
        
        if (status == 1) { // Assuming 1 means approve request
            obj.setStatus(RegistrationStatus.APPROVED.getCode());
        } else if (status == 0) { // Assuming 0 means reject request
            obj.setStatus(RegistrationStatus.REJECTED.getCode());
        }
        regrequests.save(obj);

        if (status == 1) { 
            if (students.findByRegno(obj.getRegno()) != null) throw new Exception("RegNo. already exists!");
            if (members.findByUsername(obj.getEmailid()) != null) throw new Exception("Email already exists!");
            if (members.findByMobilenumber(obj.getMobilenumber()) != null) throw new Exception("Mobile already exists!");

            Users u = new Users();
            u.setPassword(passwordEncoder.encode(CommonFuns.decode64(obj.getPassword())));
            u.setStatus(1);
            u.setType(LoginTypes.STUDENT.name());
            u.setFullname(obj.getFullname());
            u.setUsername(obj.getEmailid());
            u.setMobilenumber(obj.getMobilenumber());
            u.setCreationdate(CommonFuns.ctm());
            members.save(u);

            Student sobj = new Student();
            sobj.setUser(u.getId());
            sobj.setAddress(obj.getAddress());
            sobj.setCourse(obj.getCourse());
            sobj.setDepartment(obj.getDepartment());
            sobj.setDob(obj.getDob());
            sobj.setGender(obj.getGender());
            sobj.setParentmobile(obj.getParentmobile());
            sobj.setParentname(obj.getParentname());
            sobj.setRegno(obj.getRegno());
            sobj.setYear(obj.getYear());
            sobj.setDeposit(0);
            sobj.setStatus(1);
            students.save(sobj);
        }
    }
}
