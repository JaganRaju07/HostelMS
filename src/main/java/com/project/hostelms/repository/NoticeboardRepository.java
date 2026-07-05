package com.project.hostelms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.hostelms.entity.Noticeboard; 

public interface NoticeboardRepository extends JpaRepository<Noticeboard, Integer> {
    
	
	Noticeboard findById(int id); 
   
}


 