package com.project.hostelms.controller;
 
  
import com.project.hostelms.CommonFuns;
import com.project.hostelms.Complaintstatus;
import com.project.hostelms.Roomstatuses; 
import com.project.hostelms.LoginTypes;
import com.project.hostelms.PaymentModes;
import com.project.hostelms.entity.*;
import com.project.hostelms.repository.*;
import com.project.hostelms.service.LoginsService;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;  
import jakarta.servlet.http.HttpServletRequest;  
import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors; 

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order; 
import org.springframework.security.core.context.SecurityContextHolder;  
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody; 

@Controller
public class AdminController { 
	
 
	 
	@Autowired 
	private LoginsService adminService;  
	   
    @Resource    private UsersRepository members;
	    

    @Resource    private BlockRepository blocks;      
    @Resource    private RoomRepository rooms;         
    @Resource    private RoomstudentsRepository roomstatus;       
    @Resource    private ComplaintRepository complaints;  
    @Resource    private ComplaintcommentsRepository ccomments;       
    @Resource    private RegistrationrequestsRepository regrequests;   
    @Resource    private VisitorRepository visitors;  
    @Resource    private NoticeboardRepository           noticeboard;
    @Resource    private StudentRepository           students;
    @Resource    private DepartmentRepository departments;
    @Resource    private CourseRepository courses;
    @Resource    private SettingsRepository settings;
    @Resource    private   StudentmonthlyfeesRepository studentmonthlyfees;


	@PersistenceContext
    private EntityManager em;
	  
	 @GetMapping("/admin") 
	    public String index(Model model){
	        //model.addAttribute("users", users.findByTypeOrderByIdDesc(LoginTypes.EMPLOYEE.toString()));
	        return "redirect:/admin/dashboard";
	    } 
	    
	
 
	 
    @GetMapping("/admin/dashboard") 
    public String dashboard(Model model){   
    	model.addAttribute("blocks", blocks.count());
    	model.addAttribute("rooms", rooms.count());
    	model.addAttribute("students",roomstatus.countByStatus(1));
    	
    	model.addAttribute("neweregister",  regrequests.findByStatusOrderByIdDesc(1).size());
    	 return "admin/dashboard";
    } 
    
	 
    @GetMapping("/admin/visitors") 
    public String visitors(Model model){   
    	 model.addAttribute("blocks", blocks.findAll());
    	 return "admin/visitors";
    }  
    
    
    @PostMapping("/admin/getvisitors") 
    @ResponseBody
	   public Map < String, Object > getvisitors( HttpServletRequest request,Model model)   {
	     Map < String, Object > arr = new HashMap < > ();   
	      
	     
	     List<Integer> fts=new ArrayList<Integer>();
    	 List<Object> vrequests=new ArrayList<Object>();
    	 
    	 
    	 Map < String, Object > arr1 = new HashMap < > ();  
    	 
      
    	 
    	 for(Visitor v:visitors.findByStatus(1,Sort.by("id").ascending()))
    	 {
    		 arr1 = new HashMap < > (); 
    		 Room f=rooms.findById(v.getRoom());
    		 arr1.put("room", f.getRoomno()+"("+blocks.findById(f.getBlock()).getName()+")"); 
    		 arr1.put("visitor", v.getVisitorname());
    		 arr1.put("purpose", v.getPurpose());
    		 arr1.put("visitingtime", v.getVisitingtime());
    		 arr1.put("user", members.findById(v.getStudent()).getFullname());
    		 arr1.put("id", f.getId());
    		 vrequests.add(arr1); 
    	 }

    	 arr.put("vrequests", vrequests);
         return arr;
    }
    
    @GetMapping("/admin/changepassword") 
    public String changepassword(Model model){ 
        return "admin/changepassword";
    } 
     
   
    
    @PostMapping("/admin/updatepassword")
    public String updatepassword(HttpServletRequest request,Model m)
    {
    	String password=request.getParameter("password");
    	String newpassword=request.getParameter("newpassword");
    	String confirmpassword=request.getParameter("confirmpassword");
    	
    	Users user = adminService.findByUsername(
    		      SecurityContextHolder.getContext().getAuthentication().getName());
    		    if(newpassword.compareTo(confirmpassword)>0)
    		    {
    		    	m.addAttribute("updatepassword_errors", "New Password and Confirm Password Field do not match  !!");
    		    }
    		    else if (!adminService.checkIfValidOldPassword(user, password)) {
    		        m.addAttribute("updatepassword_errors", "Inavlid Current Password");
    		    }
    		    else if(!adminService.validation_Password(newpassword))
    		    {
    		    	  m.addAttribute("updatepassword_errors", "Inavlid New Password.Please follow specified policies.");
    		    }
    		    else
    		    {
    		    	adminService.changeUserPassword(user, newpassword);
    		      m.addAttribute("updatepassword_success", "Password updated successfully");
    		    }
    	return changepassword(m);
    } 
    
    
    @GetMapping("/admin/registrationrequests") 
    public String registrationrequests(Model model){
    	List<Registrationrequests> requests = regrequests.findByStatusOrderByIdDesc(1);

   	 model.addAttribute("courses",courses.AllAsTable());
    	
    	 model.addAttribute("requests",requests);

    	 return "admin/registrationrequests";
    } 
    
    @GetMapping("/admin/registartionaction/{id}/{status}")   
    public  String registartionaction(@PathVariable("id") int id,@PathVariable("status") int status,Model model){ 
    	 
		try {
		 Registrationrequests obj=regrequests.findById(id);
		 if(status==1)		 obj.setStatus(2);
		 if(status==0)		 obj.setStatus(0);
		 regrequests.save(obj);
		 if(status==1) { 
			  
			  if(students.findByRegno(obj.getRegno())!=null)
				  throw new Exception("RegNo. already exists!");
			  
			  if(members.findByUsername(obj.getEmailid())!=null)
				  throw new Exception("Email already exists!");
			  
			  if(members.findByMobilenumber(obj.getMobilenumber())!=null)
				  throw new Exception("Mobile already exists!");
			    
			 
		 Users u=new Users();
	       u.setPassword(CommonFuns.decode64(obj.getPassword()));
	       u.setStatus(1);
	       u.setType(LoginTypes.STUDENT.name());
	       u.setFullname(obj.getFullname()); 
	       u.setUsername(obj.getEmailid());
	       u.setMobilenumber(obj.getMobilenumber()); 
	       u.setCreationdate(CommonFuns.ctm());
	       members.save(u); 
	       adminService.changeUserPassword(u, CommonFuns.decode64(obj.getPassword()));	
	       
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
	       return "redirect:/admin/registrationrequests?success=1";
	       
		}
		catch(Exception e)
		{  
			 Registrationrequests obj=regrequests.findById(id);
		      obj.setStatus(1); 
				 regrequests.save(obj);
			   return "redirect:/admin/registrationrequests?error=1";
		} 
    }
     
     
  
    @GetMapping("/admin/blocks") 
    public String blocks(Model model){  

    	 return "admin/blocks";
    } 
    
    @PostMapping("/admin/block")
	 @ResponseBody
		public Hashtable<String,Object>  blocks(HttpServletRequest request)
		{
			Hashtable<String,Object> arr=new Hashtable<String,Object>();
			String action=request.getParameter("action");
			arr.put("success", 0);
			Block cobj;
			String name;
			int id;
			switch(action)
			{
			case "readall":
				arr.put("success", 1);
				List<Hashtable<String,Object>> lst= new ArrayList<>();
				for(Block b:blocks.findAll())
				{
					arr=new Hashtable<String,Object>();
					arr.put("id", b.getId());
					arr.put("name", b.getName());
					arr.put("rooms", rooms.findByBlock(b.getId()).size());
					lst.add(arr);
				}
				arr=new Hashtable<String,Object>();
				arr.put("success", 1);
				arr.put("data", lst);
				break;
			case "read":			
				 id=Integer.parseInt(request.getParameter("id"));
				cobj= blocks.findById(id);
				if(cobj==null)
				{
					arr.put("success", 0);
					arr.put("message", "Not Exists!");
				}
				else
				{
					arr.put("success", 1);
				   arr.put("data",cobj);
				}
				break;
			    case "add":
			    	name=request.getParameter("name"); 
			    	if(name!="")
			    	{
			    		if(blocks.findByName(name)==null)
			    		{
			    		cobj=new Block();
			    		cobj.setName(name); 
			    		blocks.save(cobj);
			    		arr.put("success", 1);
			    		arr.put("message", "Saved Successfully!");
			    		}
			    		else
			    			arr.put("message", "Name already Exists!"); 
			    	}
			    	else
			    	  arr.put("message", "Name required!");
				break;
			    case "edit":
			    	id=Integer.parseInt(request.getParameter("id"));
			    	  name=request.getParameter("name"); 
			    	  cobj= blocks.findById(id);
					if(cobj==null)
					{
						arr.put("success", 0);
						arr.put("message", "Not Exists!");
					}
					else
					{
						if(name!="")
				    	{
						cobj=blocks.findByName(name);
						if(cobj==null || cobj.getId()==id)
						{
							cobj= blocks.findById(id);
						cobj.setName(name); ; 
						blocks.save(cobj);
						arr.put("success", 1);
						arr.put("message", "Saved Successfully!");
						}
						else
							arr.put("message", "Name already Exists!");
				    	}
						else
					    	  arr.put("message", "Name required!");
					}
			    	break;
			    case "delete":
			    	id=Integer.parseInt(request.getParameter("id"));
			    	cobj= blocks.findById(id);
					if(cobj==null)
					{
						arr.put("success", 0);
						arr.put("message", "Not Exists!");
					}
					else
					{ 
						arr.put("success", 1);
						blocks.delete(cobj); 
						arr.put("message", "Deleted Successfully!");
					}
			    	break;
			}
			return arr;
		}
  
    @GetMapping({"/admin/rooms","/admin/rooms/{id}"}) 
    public String rooms(@PathVariable Optional<Integer> id,Model model){ 
    	  
  	    	 
    	 model.addAttribute("blocks", blocks.findAll()); 

    	 if(id!=null)
    	    model.addAttribute("block", blocks.findById((int)id.orElse(0))); 
    	 
    	 return "admin/rooms";
    } 
    
    
    
 
    @PostMapping("/admin/room")
	 @ResponseBody
		public Hashtable<String,Object>  rooms(HttpServletRequest request)
		{
			Hashtable<String,Object> arr=new Hashtable<String,Object>();
			String action=request.getParameter("action");
			arr.put("success", 0);
			Room cobj;
			String roomno;
			int id,floor,block,maxpersons; 
			List<Hashtable<String,Object>> lst= new ArrayList<>();
			switch(action)
			{
			case "readall":
				arr.put("success", 1);
				 lst= new ArrayList<>();
				for(Room b:rooms.findAll())
				{
					arr=new Hashtable<String,Object>();
					arr.put("id", b.getId());
					arr.put("roomno", b.getRoomno());
					arr.put("block", blocks.findById(b.getBlock()).getName());
					arr.put("floor", b.getFloor()==0?"Ground":b.getFloor());
					arr.put("maxpersons", b.getMaxpersons()); 
					lst.add(arr);
				}
				arr=new Hashtable<String,Object>();
				arr.put("success", 1);
				arr.put("data", lst); 
				break;
			case "readbyblock":
				arr.put("success", 1);
				  lst= new ArrayList<>();
				  block=Integer.parseInt(request.getParameter("block"));
				for(Room b:rooms.findByBlock(block))
				{
					arr=new Hashtable<String,Object>();
					arr.put("id", b.getId());
					arr.put("roomno", b.getRoomno());
					arr.put("block", blocks.findById(b.getBlock()).getName());
					arr.put("floor", b.getFloor());
					arr.put("maxpersons", b.getMaxpersons()); 
					lst.add(arr);
				}
				arr=new Hashtable<String,Object>();
				arr.put("success", 1);
				arr.put("data", lst); 
				break;	
			case "read":			
				 id=Integer.parseInt(request.getParameter("id"));
				cobj= rooms.findById(id);
				if(cobj==null)
				{
					arr.put("success", 0);
					arr.put("message", "Not Exists!");
				}
				else
				{
					arr.put("success", 1);
				   arr.put("data",cobj);
				}
				break;
			    case "add":
			    	roomno=request.getParameter("roomno"); 
					 floor=Integer.parseInt(request.getParameter("floor"));
					 block=Integer.parseInt(request.getParameter("block"));
					 maxpersons=Integer.parseInt(request.getParameter("maxpersons"));
			    	 
			    		if(rooms.findByRoomno(roomno)==null)
			    		{
			    		cobj=new Room();
			    		cobj.setRoomno(roomno); 
			    		cobj.setBlock(block);
			    		cobj.setFloor(floor);
			    		cobj.setMaxpersons(maxpersons); 
			    		rooms.save(cobj);
			    		arr.put("success", 1);
			    		arr.put("message", "Saved Successfully!");
			    		}
			    		else
			    			arr.put("message", "Name already Exists!"); 
			    	 
				break;
			    case "edit":
			    	id=Integer.parseInt(request.getParameter("id"));
			    	roomno=request.getParameter("roomno"); 
					 floor=Integer.parseInt(request.getParameter("floor"));
					 block=Integer.parseInt(request.getParameter("block"));
					 maxpersons=Integer.parseInt(request.getParameter("maxpersons"));
			    	 
			    	  cobj= rooms.findById(id);
					if(cobj==null)
					{
						arr.put("success", 0);
						arr.put("message", "Not Exists!");
					}
					else
					{
						 
						cobj=rooms.findByRoomno(roomno);
						if(cobj==null || cobj.getId()==id)
						{
							cobj= rooms.findById(id);
							cobj.setRoomno(roomno); 
				    		cobj.setBlock(block);
				    		cobj.setFloor(floor);
				    		cobj.setMaxpersons(maxpersons); 
						rooms.save(cobj);
						arr.put("success", 1);
						arr.put("message", "Saved Successfully!");
						}
						else
							arr.put("message", "Name already Exists!");
				    	}
					 
			    	break;
			    case "delete":
			    	id=Integer.parseInt(request.getParameter("id"));
			    	cobj= rooms.findById(id);
					if(cobj==null)
					{
						arr.put("success", 0);
						arr.put("message", "Not Exists!");
					}
					else
					{ 
						arr.put("success", 1);
						rooms.delete(cobj); 
						arr.put("message", "Deleted Successfully!");
					}
			    	break;
			}
			return arr;
		}
    
    
    @GetMapping({"/admin/courses","/admin/courses/{id}"}) 
    public String courses(@PathVariable Optional<Integer> id,Model model){ 
    	  
  	    	 
    	 model.addAttribute("departments", departments.findAll()); 

    	 if(id!=null)
    	    model.addAttribute("department", departments.findById((int)id.orElse(0))); 
    	 
    	 return "admin/courses";
    } 
    
    
    
 
    @PostMapping("/admin/course")
	 @ResponseBody
		public Hashtable<String,Object>  courses(HttpServletRequest request)
		{
			Hashtable<String,Object> arr=new Hashtable<String,Object>();
			String action=request.getParameter("action");
			arr.put("success", 0);
			Course cobj;
			String name;
			int id, department ; 
			List<Hashtable<String,Object>> lst= new ArrayList<>();
			switch(action)
			{
			case "readall":
				arr.put("success", 1);
				 lst= new ArrayList<>();
				for(Course b:courses.findAll())
				{
					arr=new Hashtable<String,Object>();
					arr.put("id", b.getId());
					arr.put("name", b.getName());
					arr.put("department", departments.findById(b.getDepartment()).getName()); 
					lst.add(arr);
				}
				arr=new Hashtable<String,Object>();
				arr.put("success", 1);
				arr.put("data", lst); 
				break;
			case "readbydepartment":
				arr.put("success", 1);
				  lst= new ArrayList<>();
				  department=Integer.parseInt(request.getParameter("department"));
				for(Course b:courses.findByDepartment(department))
				{
					arr=new Hashtable<String,Object>();
					arr.put("id", b.getId());
					arr.put("name", b.getName());
					arr.put("department", departments.findById(b.getDepartment()).getName()); 
					lst.add(arr);
				}
				arr=new Hashtable<String,Object>();
				arr.put("success", 1);
				arr.put("data", lst); 
				break;	
			case "read":			
				 id=Integer.parseInt(request.getParameter("id"));
				cobj= courses.findById(id);
				if(cobj==null)
				{
					arr.put("success", 0);
					arr.put("message", "Not Exists!");
				}
				else
				{
					arr.put("success", 1);
				   arr.put("data",cobj);
				}
				break;
			    case "add":
			    	name=request.getParameter("name");  
					 department=Integer.parseInt(request.getParameter("department")); 
			    	 
			    		if(courses.findByName(name)==null)
			    		{
			    		cobj=new Course();
			    		cobj.setName(name); 
			    		cobj.setDepartment(department); 
			    		courses.save(cobj);
			    		arr.put("success", 1);
			    		arr.put("message", "Saved Successfully!");
			    		}
			    		else
			    			arr.put("message", "Name already Exists!"); 
			    	 
				break;
			    case "edit":
			    	id=Integer.parseInt(request.getParameter("id"));
			    	name=request.getParameter("name");  
					 department=Integer.parseInt(request.getParameter("department")); 
			    	 
			    	  cobj= courses.findById(id);
					if(cobj==null)
					{
						arr.put("success", 0);
						arr.put("message", "Not Exists!");
					}
					else
					{
						 
						cobj=courses.findByName(name);
						if(cobj==null || cobj.getId()==id)
						{
							cobj= courses.findById(id);
							cobj.setName(name); 
				    		cobj.setDepartment(department); 
						courses.save(cobj);
						arr.put("success", 1);
						arr.put("message", "Saved Successfully!");
						}
						else
							arr.put("message", "Name already Exists!");
				    	}
					 
			    	break;
			    case "delete":
			    	id=Integer.parseInt(request.getParameter("id"));
			    	cobj= courses.findById(id);
					if(cobj==null)
					{
						arr.put("success", 0);
						arr.put("message", "Not Exists!");
					}
					else
					{ 
						arr.put("success", 1);
						courses.delete(cobj); 
						arr.put("message", "Deleted Successfully!");
					}
			    	break;
			}
			return arr;
		}
    
    @GetMapping("/admin/members") 
    public String regusers(Model model){
    	List<Map < String, Object >> arr = new ArrayList<Map < String, Object >> ();
    	Map < String, Object > obj = new HashMap < > ();
    	Users obj1=new Users();
    	for(Users s:members.findByTypeOrderByFullnameAsc(LoginTypes.STUDENT.name())) 
    	{
    		obj = new HashMap < > ();
    		obj1=adminService.findByUsername(s.getUsername());
    		obj.put("id", obj1.getId());
    		obj.put("fullname", s.getFullname());
    		obj.put("emailid", s.getUsername());
    		obj.put("mobilenumber", s.getMobilenumber()); 
    		obj.put("regdate", s.getCreationdate().toLocalDateTime().toLocalDate()); 
    		obj.put("status", obj1.getStatus());
    		arr.add(obj);
    	}
    	model.addAttribute("regusers",arr);
        return "admin/members";
    } 
    
 
    @PostMapping("/admin/changeuserstatus/{id}/{status}")  
	 @ResponseBody
    public  Map < String, Object > changeuserstatus(@PathVariable("id") int id,@PathVariable("status") int status,Model model){ 
    	 Map < String, Object > arr = new HashMap < > ();
		 
		arr.put("success",  1); 
		try {
		 Users obj=members.findById(id);
		 obj.setStatus(status);
		 members.save(obj);
		arr.put("html",  " Changed Successfully! ");
		}
		catch(Exception e)
		{
			arr.put("success",  0);
			arr.put("html",  "Error in changing!");
		}
		return arr;
    }
    
    @GetMapping("/admin/profile")
    public String myprofile(Model model) {
    	
    	Users s = adminService.findByUsername(
    		      SecurityContextHolder.getContext().getAuthentication().getName());
    	 
    	model.addAttribute("fullname", s.getFullname()); 
    	model.addAttribute("mobileno", s.getMobilenumber());  
    	model.addAttribute("id",s.getId());   
    	model.addAttribute("username",s.getUsername());   
    	model.addAttribute("regdate", s.getCreationdate().toLocalDateTime().toLocalDate()); 
    	model.addAttribute("updationtime", s.getUpdationdate()); 
    	
    	return "admin/myprofile";
    } 
    
    @PostMapping("/admin/updateprofile") 
	   public String updateprofile( HttpServletRequest request,Model model)   { 

	     String fullname = request.getParameter("fullname");   
	     String mobileno =  request.getParameter("mobileno");    
	     
	     List < String > errors = new ArrayList < String > ();
	    	Users user = adminService.findByUsername(
	    		      SecurityContextHolder.getContext().getAuthentication().getName());
	     Users obj;
		if (mobileno.trim() .isBlank() || mobileno == null)
	       errors.add("Mobile no");
	     else
	     {
	        obj = members.findByMobilenumber(mobileno);	   
	      if (obj != null && obj.getId()!=members.findByUsername(user.getUsername()).getId())
	         errors.add("Mobile no exists!");
	     } 
	     
	     if (  fullname == null || fullname.trim().isBlank()) errors.add("Full name");

	     if (errors.size() == 0) {  
	       obj = members.findByUsername(user.getUsername()); 
	       obj.setFullname(fullname);
	       obj.setMobilenumber(mobileno); 
	       members.save(obj);
	       model.addAttribute("success",  " Updated successfully!."); 
	       
	     } else
	    	 model.addAttribute("error",  "The following fields contains errors: " + errors.stream().collect(Collectors.joining(", ")));

	     return  myprofile(model);
	   } 
    
    
    
   
   
    @GetMapping("/admin/complaints") 
    public String complaints(Model model){ 
    	    
    	 
    	 Map < String, Object > arr = new HashMap < > ();  
    	 List<Object> cmts=new ArrayList<Object>();
    	 for(Complaint c:complaints.findAll(Sort.by(Order.desc("id"))))
    	 {
    		 arr= new HashMap < > ();  
    		 arr.put("id", c.getId());
    		 arr.put("ctitle", c.getCtitle());
    		 arr.put("user",members.findById(c.getStudent()).getFullname());
    		 arr.put("creationdate",CommonFuns.Formatdt(c.getCreationdate()));
    		 arr.put("status",Complaintstatus.getName(c.getStatus()));
    		 cmts.add(arr);
    	 }
    	 model.addAttribute("complaints", cmts);
         model.addAttribute("statuses", Complaintstatus.values());
    	 return "admin/complaints";
    } 
    
    @PostMapping("/admin/addcomplaintcommentdb") 
    @ResponseBody
		 public Map < String, Object > addcomplaintcommentdb( HttpServletRequest request,Model model)   {
	     Map < String, Object > arr = new HashMap < > ();  

	     String comment = request.getParameter("comment");   
	     int id = Integer.parseInt(request.getParameter("id"));
	     int status = Integer.parseInt(request.getParameter("status"));
	        
    	 
	     List < String > errors = new ArrayList < String > ();
	  
	     
	     if (comment.trim() .isBlank() || comment == null)
	       errors.add("Comment");
	     Users user = adminService.findByUsername(
   		      SecurityContextHolder.getContext().getAuthentication().getName()); 
	     
	     if (complaints.findById(id) == null)
		       errors.add("Complaint not found");
	        
	      arr.put("success",  0); 
	     if (errors.size() == 0) {  
	       Complaint cobj=complaints.findById(id);
	       cobj.setStatus(status);
	       complaints.save(cobj);
	       Complaintcomments obj = new Complaintcomments();
	       obj.setComment(comment) ; 
	       obj.setComplaint(id);
	       obj.setUser(user.getFullname()+"(#"+user.getId()+")");
	       obj.setCreationdate(CommonFuns.ctm());
	       ccomments.save(obj); 
	    	   arr.put("success",  1); 
		       arr.put("html",  "Submitted successfully!.");   
	     } else
	    	 arr.put("html",  "The following fields contains errors: " + errors.stream().collect(Collectors.joining(", ")));

	     return  arr;
	   }
    
    @GetMapping("/admin/getcomplaint/{id}") 
    @ResponseBody
    public Map < String, Object > getcomplaint(@PathVariable int id){   
    	    Complaint cobj=complaints.findById(id);
    	    Map < String, Object > arr = new HashMap < > ();
    	    arr.put("complaint", cobj);
    	    Room f=rooms.findById(cobj.getRoom());
    	    arr.put("room",f.getRoomno()+"("+blocks.findById(f.getBlock()).getName()+")");
    	    arr.put("status", cobj.getStatus()); 
    	    arr.put("comments", ccomments.findByComplaintOrderByIdDesc(id));
    	    arr.put("creationdate",CommonFuns.Formatdt(cobj.getCreationdate())); 
    	    return arr;
    } 
    
     

    @GetMapping("/admin/addnoticeboard")
	    public String addnoticeboard(Model m) {   
	    	return "/admin/addnoticeboard";
	    }
	    
    @GetMapping("/admin/noticeboard")
    public String noticeboard(Model m) {   
    	return "/admin/noticeboard";
    }
	    
    @GetMapping("/admin/editnoticeboard/{id}")
    public String editnoticeboard(@PathVariable int id,Model m) {   
    	m.addAttribute("notice", noticeboard.findById(id));
    	return "/admin/editnoticeboard";
    }
    
	    @PostMapping("/admin/noticeboard")
		 @ResponseBody
			public Hashtable<String,Object>  noticeboards(HttpServletRequest request)  
			{

	    	Users admin = adminService.findByUsername(
	  		      SecurityContextHolder.getContext().getAuthentication().getName());
	    	
				Hashtable<String,Object> arr=new Hashtable<String,Object>();
				String action=request.getParameter("action");
				arr.put("success", 0);
				Noticeboard cobj;
				String subject,message;
				int id,status; 
				switch(action)
				{ 
				case "read":

			    	id=Integer.parseInt(request.getParameter("id"));  
						arr.put("success", 1);
						arr.put("data", noticeboard.findById(id));
						
						break;
				case "readall":
					 List<Hashtable<String, Object>> lst = new ArrayList<>();
						for(Noticeboard b:noticeboard.findAll())
				 			{
				 				 arr = new Hashtable<String,Object>();
				 				arr.put("id", b.getId());  
				 				arr.put("subject", b.getSubject());  
				 				arr.put("creationtime", b.getCreationdate()); 
				 				arr.put("status", b.getStatus());  
				 				lst.add(arr);
				 			}
						arr = new Hashtable<String,Object>();
						arr.put("success", 1);
						arr.put("data", lst);
						
						break;
				    case "add":
				    	subject=request.getParameter("subject"); 
				    	message=request.getParameter("message");  
				    	if(subject!="")
				    	{ 
				    		cobj=new Noticeboard();
				    		cobj.setSubject(subject); 
				    		cobj.setMessage(message);
				    		cobj.setStatus(1);
				    		noticeboard.save(cobj);
				    		arr.put("success", 1);
				    		arr.put("message", "Saved Successfully!"); 
				    	}
				    	else
				    	  arr.put("message", "Title required!");
					break;
				    case "edit":
				    	id=Integer.parseInt(request.getParameter("id"));  
				    	subject=request.getParameter("subject"); 
				    	message=request.getParameter("message");  
				    	status=Integer.parseInt(request.getParameter("status"));  
				    	if(subject!="")
				    	{ 
				    		cobj=noticeboard.findById(id);
				    		cobj.setSubject(subject); 
				    		cobj.setMessage(message);
				    		cobj.setStatus(status);
				    		noticeboard.save(cobj);
				    		arr.put("success", 1);
				    		arr.put("message", "Saved Successfully!"); 
				    	}
				    	else
				    	  arr.put("message", "Title required!");
					break;
				    case "delete":
				    	id=Integer.parseInt(request.getParameter("id"));
				    	cobj= noticeboard.findById(id);
						if(cobj==null)
						{
							arr.put("success", 0);
							arr.put("message", "Not Exists!");
						}
						else
						{ 
							arr.put("success", 1);
							noticeboard.delete(cobj); 
							arr.put("message", "Deleted Successfully!"); 
						}
				    	break;
				}
				return arr;
			}
	    
	    

	    @GetMapping("/admin/departments") 
	    public String departments(Model model){  

	    	 return "admin/departments";
	    } 
	    
	    @PostMapping("/admin/department")
		 @ResponseBody
			public Hashtable<String,Object>  departments(HttpServletRequest request)
			{
				Hashtable<String,Object> arr=new Hashtable<String,Object>();
				String action=request.getParameter("action");
				arr.put("success", 0);
				Department cobj;
				String name;
				int id;
				switch(action)
				{
				case "readall":
					arr.put("success", 1);
					List<Hashtable<String,Object>> lst= new ArrayList<>();
					for(Department b:departments.findAll())
					{
						arr=new Hashtable<String,Object>();
						arr.put("id", b.getId());
						arr.put("name", b.getName()); 
						lst.add(arr);
					}
					arr=new Hashtable<String,Object>();
					arr.put("success", 1);
					arr.put("data", lst);
					break;
				case "read":			
					 id=Integer.parseInt(request.getParameter("id"));
					cobj= departments.findById(id);
					if(cobj==null)
					{
						arr.put("success", 0);
						arr.put("message", "Not Exists!");
					}
					else
					{
						arr.put("success", 1);
					   arr.put("data",cobj);
					}
					break;
				    case "add":
				    	name=request.getParameter("name"); 
				    	if(name!="")
				    	{
				    		if(departments.findByName(name)==null)
				    		{
				    		cobj=new Department();
				    		cobj.setName(name); 
				    		departments.save(cobj);
				    		arr.put("success", 1);
				    		arr.put("message", "Saved Successfully!");
				    		}
				    		else
				    			arr.put("message", "Name already Exists!"); 
				    	}
				    	else
				    	  arr.put("message", "Name required!");
					break;
				    case "edit":
				    	id=Integer.parseInt(request.getParameter("id"));
				    	  name=request.getParameter("name"); 
				    	  cobj= departments.findById(id);
						if(cobj==null)
						{
							arr.put("success", 0);
							arr.put("message", "Not Exists!");
						}
						else
						{
							if(name!="")
					    	{
							cobj=departments.findByName(name);
							if(cobj==null || cobj.getId()==id)
							{
								cobj= departments.findById(id);
							cobj.setName(name); ; 
							departments.save(cobj);
							arr.put("success", 1);
							arr.put("message", "Saved Successfully!");
							}
							else
								arr.put("message", "Name already Exists!");
					    	}
							else
						    	  arr.put("message", "Name required!");
						}
				    	break;
				    case "delete":
				    	id=Integer.parseInt(request.getParameter("id"));
				    	cobj= departments.findById(id);
						if(cobj==null)
						{
							arr.put("success", 0);
							arr.put("message", "Not Exists!");
						}
						else
						{ 
							arr.put("success", 1);
							departments.delete(cobj); 
							arr.put("message", "Deleted Successfully!");
						}
				    	break;
				}
				return arr;
			}
	    
  
	    @GetMapping("/admin/addstudent")
	    public String addstudent(Model m) {   
	    	m.addAttribute("departments", departments.findAll());
	    	return "/admin/addstudent";
	    }
	    
    @GetMapping("/admin/studentmanage")
    public String studentmanage(Model m) {   
    	return "/admin/students";
    }
	    
    @GetMapping("/admin/editstudent/{id}")
    public String editstudent(@PathVariable int id,Model m) {   
    	m.addAttribute("student", students.findByUser(id));
    	m.addAttribute("departments", departments.findAll());
    	m.addAttribute("user", members.findById(id));
    	return "/admin/editstudent";
    }
    
	    @PostMapping("/admin/student")
		 @ResponseBody
			public Hashtable<String,Object>  students(HttpServletRequest request)  
			{ 
				Hashtable<String,Object> arr=new Hashtable<String,Object>();
				 List<Hashtable<String, Object>> lst = new ArrayList<>();
				String action=request.getParameter("action");
				arr.put("success", 0);
				Student sobj;
				Users uobj;
				String name,email,mobile,address,parentname,parentmobile,regno,gender;
				int id,status,department,course,year;
				String password;
				float deposit;
				Date dob;
				try {
				switch(action)
				{ 
				case "read":

			    	id=Integer.parseInt(request.getParameter("id"));  
						arr.put("success", 1);
						arr.put("data.student", students.findByUser(id));
						arr.put("data.user", members.findById(id));						
						break;
				case "readall":
					 lst = new ArrayList<>();
						for(Users u:members.findByTypeOrderByFullnameAsc(LoginTypes.STUDENT.name()))
				 			{
				 				 arr = new Hashtable<String,Object>();
				 				arr.put("id", u.getId());   
				 				arr.put("name", u.getFullname()); 
				 				arr.put("mobile", u.getMobilenumber());
				 				Student s=students.findByUser(u.getId());
				 				arr.put("course", courses.findById( s.getCourse()).getName());
				 				List<Roomstudents> rs = roomstatus.findByStudentAndStatus(u.getId(), 1);
				 				arr.put("room", rs.size()==0?"NA":rooms.findById(rs.get(0).getRoom()).getRoomno());
				 				arr.put("creationtime", u.getCreationdate()); 
				 				arr.put("status", u.getStatus());  
				 				lst.add(arr);
				 			}
						arr = new Hashtable<String,Object>();
						arr.put("success", 1);
						arr.put("data", lst);
						
						break;
						
				case "readbystatus":
				     lst = new ArrayList<>(); 
				    	status=Integer.parseInt(request.getParameter("status"));  
						for(Users u:members.findByTypeAndStatus(LoginTypes.STUDENT.name(),status))
				 			{
				 				 arr = new Hashtable<String,Object>();
				 				arr.put("id", u.getId());   
				 				arr.put("name", u.getFullname()); 
				 				arr.put("mobile", u.getMobilenumber());
				 				Student s=students.findByUser(u.getId());
				 				arr.put("course", courses.findById( s.getCourse()).getName());
				 				List<Roomstudents> rs = roomstatus.findByStudentAndStatus(u.getId(), 1);
				 				arr.put("room", rs.size()==0?"NA":rooms.findById(rs.get(0).getRoom()).getRoomno());
				 				arr.put("creationtime", u.getCreationdate()); 
				 				arr.put("status", u.getStatus());  
				 				lst.add(arr);
				 			}
						arr = new Hashtable<String,Object>();
						arr.put("success", 1);
						arr.put("data", lst);
						
						break;
				    case "add":  
						  name=request.getParameter("name"); 
						  email=request.getParameter("email");  
						  mobile=request.getParameter("mobile");  
						  address=request.getParameter("address");  
						  parentname=request.getParameter("parentname");  
						  parentmobile=request.getParameter("parentmobile");   
						  password=request.getParameter("password");   
						  department=Integer.parseInt(request.getParameter("department"));   
						  course=Integer.parseInt(request.getParameter("course"));  
						  year=Integer.parseInt(request.getParameter("year"));  
						  dob=Date.valueOf(request.getParameter("dob"));
						  regno=request.getParameter("regno"); 
						  gender=request.getParameter("gender");  
						  deposit=Float.parseFloat(request.getParameter("deposit"));  
						  
						  if(adminService.validation_Password(password)==false)
							  throw new Exception("Password should be strong!");
						  
						  if(students.findByRegno(regno)!=null)
							  throw new Exception("RegNo. already exists!");
						  
						  if(members.findByUsername(email)!=null)
							  throw new Exception("Email already exists!");
						  
						  if(members.findByMobilenumber(mobile)!=null)
							  throw new Exception("Mobile already exists!");
						  
						    uobj=new Users();
						    uobj.setFullname(name);
						    uobj.setMobilenumber(mobile);
						    uobj.setUsername(email);
						    uobj.setType(LoginTypes.STUDENT.name());
						    uobj.setStatus(1);				    	
						    uobj.setPassword(password);
						    members.save(uobj);
						    adminService.changeUserPassword(uobj, password);
						    sobj=new Student(); 
						    sobj.setUser(uobj.getId());
						    sobj.setAddress(address);
						    sobj.setCourse(course);
						    sobj.setDepartment(department);
						    sobj.setDob(dob);
						    sobj.setGender(gender);
						    sobj.setParentmobile(parentmobile);
						    sobj.setParentname(parentname);
						    sobj.setRegno(regno);
						    sobj.setYear(year);
						    sobj.setDeposit(deposit);
				    		sobj.setStatus(1);
				    		students.save(sobj);
				    		arr.put("success", 1);
				    		arr.put("message", "Saved Successfully!"); 
				    	 
					break;
				    case "edit":
				    	id=Integer.parseInt(request.getParameter("id"));  

						  name=request.getParameter("name"); 
						  email=request.getParameter("email");  
						  mobile=request.getParameter("mobile");  
						  address=request.getParameter("address");  
						  parentname=request.getParameter("parentname");  
						  parentmobile=request.getParameter("parentmobile");   
						  password=request.getParameter("password");   
						  department=Integer.parseInt(request.getParameter("department"));   
						  course=Integer.parseInt(request.getParameter("course"));  
						  year=Integer.parseInt(request.getParameter("year"));  
						  dob=Date.valueOf(request.getParameter("dob"));
						  regno=request.getParameter("regno"); 
						  gender=request.getParameter("gender");  
					     status=Integer.parseInt(request.getParameter("status"));  
						  deposit=Float.parseFloat(request.getParameter("deposit"));  
						  
						  if(!password.isBlank() && adminService.validation_Password(password)==false)
							  throw new Exception("Password should be strong!");
				     
						  sobj=students.findByRegno(regno);
						  
						  if(sobj!=null && sobj.getUser()!=id)
							  throw new Exception("RegNo. already exists!");
						  
						  uobj=members.findByUsername(email);
						  if(uobj!=null && uobj.getId()!=id)
							  throw new Exception("Email already exists!");

						  uobj=members.findByMobilenumber(mobile);
						  if(uobj!=null && uobj.getId()!=id)
							  throw new Exception("Mobile already exists!");
						  
				    	    uobj=members.findById(id);
						    uobj.setFullname(name);
						    uobj.setMobilenumber(mobile); 
						    uobj.setStatus(status);			 
						    members.save(uobj);

							  if(!password.isBlank()) 
								    adminService.changeUserPassword(uobj, password);
							  
				    		sobj=students.findByUser(uobj.getId());  
						    sobj.setAddress(address);
						    sobj.setCourse(course);
						    sobj.setDepartment(department);
						    sobj.setDob(dob);
						    sobj.setGender(gender);
						    sobj.setParentmobile(parentmobile);
						    sobj.setParentname(parentname);
						    sobj.setRegno(regno);
						    sobj.setYear(year);
						    sobj.setDeposit(deposit);
				    		sobj.setStatus(status);
				    		students.save(sobj);
				    		arr.put("success", 1);
				    		arr.put("message", "Saved Successfully!"); 
				    	 
					break;
				    case "searchforallocation": 
				    	List<Integer>  sids=roomstatus.findByStatus(1).stream().map(e->e.getStudent()).toList();
				    	if(sids.size()==0)
				    		{
				    		 sids=new ArrayList<>();
				    		  sids.add(Integer.parseInt("0"));
				    		}
				    	arr.put("data" ,new ArrayList<Hashtable<String,Object>>());
				    	for(Users c:members.findByIdNotInAndStatus(sids,1))
				    	{
				    		if(c.getType().compareTo(LoginTypes.STUDENT.name())==0)
				    		{
				    		Hashtable<String, Object> obj = new Hashtable<String,Object>(); 
				        	obj.put("id",c.getId()); 
				        	obj.put("name", c.getFullname()); 
				    		((List<Hashtable<String,Object>>)arr.get("data")).add(obj);
				    	} 
				    	}
			    		arr.put("success", 1);
				    	break;
				}
				}
				catch(Exception e)
				{ 
		    		arr.put("success", 0);
		    		arr.put("message", e.getMessage()); 
				}
				return arr;
			}
	    
	    
	    @GetMapping("/admin/allocateroom")
	    public String allocateroom(Model m)
	    {
	    	return "/admin/allocateroom";
	    }
	    
	    
	    @GetMapping("/admin/roomallocation/{id}")
	    public String roomallocation(@PathVariable int id,Model m)
	    {
	    	m.addAttribute("room", id);
	    	return "/admin/roomallocation";
	    }
	    
	        @PostMapping("/admin/allocateroom")
	 		 @ResponseBody
	 			public Hashtable<String,Object>  allocateroom(HttpServletRequest request)
	 			{
	 				Hashtable<String,Object> arr=new Hashtable<String,Object>();
	 				String action=request.getParameter("action");
	 				arr.put("success", 0);  
	 				int student,room;
	 				Date fromdate,todate;
	 				float rent;
	 				ArrayList<Object> lst;
					switch(action)
	 				{
	 				case "readall":
	 					arr.put("success", 1);
	 					 lst= new ArrayList<>();
	 					for(Room b:rooms.findAll())
	 					{
	 						arr=new Hashtable<String,Object>();
	 						arr.put("id", b.getId());
	 						arr.put("roomno", b.getRoomno());
	 						arr.put("block", blocks.findById(b.getBlock()).getName());
	 						arr.put("floor", b.getFloor()==0?"Ground":b.getFloor());
	 						arr.put("maxpersons", b.getMaxpersons()); 
	 						arr.put("persons", roomstatus.countByRoomAndStatus(b.getId(), 1));
	 						lst.add(arr);
	 					}
	 					arr=new Hashtable<String,Object>();
	 					arr.put("success", 1);
	 					arr.put("data", lst);  
	 					break;
	 				case "read":			
	 					 room=Integer.parseInt(request.getParameter("room"));
	 					lst= new ArrayList<>();
	 					for(Roomstudents bs:roomstatus.findByRoomAndStatus(room, 1))
	 					{
	 						arr=new Hashtable<String,Object>();
	 						arr.put("id", bs.getId());   
	 						arr.put("student", members.findById(bs.getStudent()).getFullname());   
	 						arr.put("fromdate",CommonFuns.Formatdt(bs.getFromdt()));   
	 						arr.put("monthlyrent", bs.getMonthlyrent());  
	 						lst.add(arr);
	 					}
	 					arr=new Hashtable<String,Object>();
	 					Room b=rooms.findById(room);
 						arr.put("roomno", b.getRoomno());
 						arr.put("block", blocks.findById(b.getBlock()).getName());
 						arr.put("floor", b.getFloor()==0?"Ground":b.getFloor());
 						arr.put("maxpersons", b.getMaxpersons()); 
	 					arr.put("success", 1);
	 					arr.put("data", lst);  
	 					break; 
	 				case "readbyid":			
	 					int id=Integer.parseInt(request.getParameter("id")); 
	 					 Roomstudents bs=roomstatus.findById(id); 
	 						arr=new Hashtable<String,Object>();
	 						arr.put("id", bs.getId());   
	 						arr.put("student", members.findById(bs.getStudent()).getFullname());   
	 						arr.put("fromdate", bs.getFromdt());   
	 						arr.put("monthlyrent", bs.getMonthlyrent());   
	 					 
	 					arr.put("success", 1);  
	 					break; 
	 				    case "allocateroom": 
	 				    	room=Integer.parseInt(request.getParameter("room"));  
	 				    		student=Integer.parseInt(request.getParameter("student")); 
	 				    		fromdate=Date.valueOf(request.getParameter("fromdate")); 
	 				    		rent=Float.parseFloat(request.getParameter("rent")); 
	 				    		Roomstudents rs=roomstatus.findByRoomAndStudent(room,student);
	 				    		
	 				    		rs=new Roomstudents();
	 				    		
	 				    		rs.setRoom(room);
	 				    		rs.setStudent(student);
	 				    		rs.setFromdt(fromdate);
	 				    		rs.setMonthlyrent(rent);
	 				    		rs.setStatus(1);
	 				    		 
	 				    		roomstatus.save(rs); 
	 		 					arr.put("success", 1);
	 		 					arr.put("message", "Allocated Successfully");
	 				    	break; 
	 				    	
	 				   case "deallocateroom": 
	 					          id=Integer.parseInt(request.getParameter("id"));   
	 					        todate=Date.valueOf(request.getParameter("todate"));  
	 				    		  rs=roomstatus.findById(id);
	 				    		  rs.setTodt(todate);
	 				    		 rs.setStatus(0);
	 				    		roomstatus.save(rs); 
	 		 					arr.put("success", 1);
	 		 					arr.put("message", "Deallocated Successfully");
	 				    	break; 
	 				    	
	 				}
	 				return arr;
	 			}
	        @GetMapping("/admin/settings") 
		    public String settings(Model model,HttpServletRequest request){
			    Hashtable<String,String> setting=new  Hashtable<String,String>();
			    
			    for(Setting s:settings.findAll())
			      setting.put(s.getSkey(), s.getSval());
			    
			    model.addAttribute("settings", setting);
		     
			    return "/admin/settings";
		    } 
		 
	        @PostMapping("/admin/savesettings")
	 	   public @ResponseBody Map<String,Object>   savesettings(HttpServletRequest request)
	 		{
	 			Hashtable<String,Object> arr=new Hashtable<String,Object>(); 
	 			arr.put("success", 0);

	 			 for(Setting s:settings.findAll())
	 			 {
	 				 if(request.getParameter("settings["+s.getSkey()+"]")!=null)
	 				 {
	 				   s.setSval(request.getParameter("settings["+s.getSkey()+"]"));
	 				   settings.save(s);
	 				 }
	 			 }
	 		     arr.put("success", 1);
	 		     arr.put("message","Settings Saved!");
	 		    return arr;
	 		}

	        @GetMapping("/admin/generatefees") 
	        public String generatefees(Model model){ 
	            return "admin/generatefees";
	        } 
	        
	         
	        @PostMapping("/admin/newgeneratefees")
		 	   public @ResponseBody Map<String,Object>   newgeneratefees(HttpServletRequest request)
		 		{
		 			Hashtable<String,Object> arr=new Hashtable<String,Object>(); 
		 			
		 			String monthyear=request.getParameter("monthyear"); 
		 			LocalDate ld = Date.valueOf(monthyear+"-01").toLocalDate() ;
		 			YearMonth ym = YearMonth.from( ld ) ; 
		 			int days=ym.lengthOfMonth();
		 			
		 			ArrayList<Object> lst = new ArrayList<>();
 					for(Roomstudents bs:roomstatus.findAll())
 					{
 						arr=new Hashtable<String,Object>();
 						int tdays=days;
 						if(bs.getFromdt().toLocalDate().getDayOfMonth()>10 &&(bs.getFromdt().toLocalDate().getMonthValue()==ld.getMonthValue() && bs.getFromdt().toLocalDate().getYear()==ld.getYear()))
 						{
 							tdays=(days-bs.getFromdt().toLocalDate().getDayOfMonth());					
 						}
 						else if(bs.getTodt()!=null && bs.getTodt().toLocalDate().getMonthValue()==ld.getMonthValue() && bs.getTodt().toLocalDate().getYear()==ld.getYear())
 						{	
 							tdays= bs.getTodt().toLocalDate().getDayOfMonth() ;
 					 	} 
 						 						
 						 if(bs.getTodt()!=null && bs.getTodt().toLocalDate().getMonthValue()<ld.getMonthValue() && bs.getTodt().toLocalDate().getYear()<=ld.getYear())
  						{	
  							tdays= 0 ;
  					 	}
 						Studentmonthlyfees sm = studentmonthlyfees.findByUserAndRoomAndMonthAndYear(bs.getStudent(),bs.getRoom(),ld.getMonthValue(),ld.getYear());
 						if(sm!=null && sm.getPaid()==1)
 						{
 							tdays=0;
 						}
 						 
 							
 						 if(  bs.getFromdt().toLocalDate().getMonthValue()>ld.getMonthValue() && bs.getFromdt().toLocalDate().getYear()>=ld.getYear())
   						{	
   							tdays= 0 ;
   					 	} 
 						if(tdays>0)
 						{ 
 	 						  arr.put("id", bs.getId());    
 	 						  arr.put("room",rooms.findById(bs.getRoom()).getRoomno());   
 	 						  arr.put("student", members.findById(bs.getStudent()).getFullname()); 
 							  arr.put("monthlyrent",Math.round( (bs.getMonthlyrent()/days)*tdays));    
 		 					  arr.put("days", tdays);  
 		 					  arr.put("mess",Math.round(tdays*CommonFuns.cfloat(settings.findBySkey("meal_rate").getSval())*CommonFuns.cfloat(settings.findBySkey("meal_perday").getSval()))); 
 	 	                  float other= CommonFuns.cfloat(settings.findBySkey("other_charges").getSval()) ;
 	 	                  List<Studentmonthlyfees> sm1 = studentmonthlyfees.findByUserAndMonthAndYear(bs.getStudent(), ld.getMonthValue(),ld.getYear());
 	 	                 
 	 	                  if(sm1.size()>=1 && sm==null)
 	 	                	      other=0;  
 	 	                  
 	 	                  if(sm1.size()>1 && sm!=null)
 	 	                  { 
 	 	                	  for(Studentmonthlyfees s1:sm1)
 	 	                		  if(s1.getId()!=sm.getId() && s1.getOther()!=0)
 	 	                			other=0;
 	 	                  }
 	 	                	  
 						  if(sm==null)	  sm =new Studentmonthlyfees();
 						  sm.setUser(bs.getStudent());
 						  sm.setRoom(bs.getRoom());
 						  sm.setMonth(ld.getMonthValue());
 						  sm.setYear(ld.getYear());
 						  sm.setPaid(0);
 						  sm.setDays(tdays);
 						  sm.setRent(Math.round( (bs.getMonthlyrent()/days)*tdays));
 						  sm.setMess(Math.round(tdays*CommonFuns.cfloat(settings.findBySkey("meal_rate").getSval())*CommonFuns.cfloat(settings.findBySkey("meal_perday").getSval())));
 						  sm.setOther( other);
 						  sm.setTotalfees(sm.getRent()+sm.getMess()+sm.getOther());
 						  studentmonthlyfees.save(sm);
 						 
		 				  arr.put("other", other);  
 						  lst.add(arr);
 					    } 
 					}
 					arr=new Hashtable<String,Object>(); 
 					arr.put("data", lst);
		 		    arr.put("success", 1); 
		 		    return arr;
		 		}
	        
	        @GetMapping("/admin/managefees") 
	        public String managefees(Model model){ 
	            return "admin/managefees";
	        } 
	        
	        
	        
	        @PostMapping("/admin/fetchfees")
		 	   public @ResponseBody Map<String,Object>   fetchfees(HttpServletRequest request)
		 		{
		 			Hashtable<String,Object> arr=new Hashtable<String,Object>(); 
		 			
		 			String monthyear=request.getParameter("monthyear"); 
		 			LocalDate ld = Date.valueOf(monthyear+"-01").toLocalDate() ;
		 			YearMonth ym = YearMonth.from( ld ) ; 
		 			int days=ym.lengthOfMonth();
		 			
		 			ArrayList<Object> lst = new ArrayList<>();
 					for(Studentmonthlyfees sm : studentmonthlyfees.findByMonthAndYear(ld.getMonthValue(),ld.getYear()))
 					{
 						arr=new Hashtable<String,Object>();
 						
 	 				    arr.put("id", sm.getId());    
 	 				    arr.put("room",rooms.findById(sm.getRoom()).getRoomno());   
 	 					arr.put("student", members.findById(sm.getUser()).getFullname());   
		 				arr.put("totalfees", sm.getTotalfees());   
		 				arr.put("paiddate", sm.getPaiddate()==null?"":sm.getPaiddate());  
		 				arr.put("paidby", sm.getPaidby()==null?"":sm.getPaidby());  
		 				arr.put("paid", sm.getPaid());  
 						lst.add(arr); 
 					}
 					arr=new Hashtable<String,Object>(); 
 					arr.put("data", lst);
		 		    arr.put("success", 1); 
		 		    return arr;
		 		}
	        
	        @PostMapping("/admin/feespaid")
		 	   public @ResponseBody Map<String,Object>   feespaid(HttpServletRequest request)
		 		{
		 			Hashtable<String,Object> arr=new Hashtable<String,Object>(); 
		 			int id=Integer.parseInt(request.getParameter("id"));   
				      String  paidby=request.getParameter("paidby");  
				      Studentmonthlyfees sm =studentmonthlyfees.findById(id);
			    		  sm.setPaid(1);
			    		  sm.setPaidby(paidby);
			    		  sm.setPaiddate(CommonFuns.ctm());
			    		 studentmonthlyfees.save(sm); 
	 					arr.put("success", 1);
	 					arr.put("message", "Updated Successfully");
		 			 return arr;
		 			
		 		}
}