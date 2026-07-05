package com.project.hostelms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.hostelms.entity.Complaintcomments; 

public interface ComplaintcommentsRepository extends JpaRepository<Complaintcomments, Integer> {
    
	
	Complaintcomments findById(int id);

	List<Complaintcomments> findByComplaintOrderByIdDesc(int c); 
   
}


 