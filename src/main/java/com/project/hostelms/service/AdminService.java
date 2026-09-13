package com.project.hostelms.service;

import com.project.hostelms.dto.VisitorDTO;
import java.util.List;

public interface AdminService {
    List<VisitorDTO> getVisitors();
    void processRegistration(int id, int status) throws Exception;
}
