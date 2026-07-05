package com.project.hostelms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.hostelms.entity.Complaint;
import com.project.hostelms.entity.Room; 

public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {
    
	
	Complaint findById(int id);

	List<Complaint> findByStudentOrderByIdDesc(int user);

	List<Complaint> findByStudentOrderByIdAsc(int id); 
   
}


 