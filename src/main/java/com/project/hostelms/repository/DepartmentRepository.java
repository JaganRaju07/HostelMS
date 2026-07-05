package com.project.hostelms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.hostelms.entity.Department; 

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    
	
	Department findById(int id);

	Department findByName(String name); 
   
}


 