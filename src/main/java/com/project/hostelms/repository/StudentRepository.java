package com.project.hostelms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.hostelms.entity.Student; 

public interface StudentRepository extends JpaRepository<Student, Integer> {
    
	
	Student findById(int id);

	Student findByUser(int id);

	Student findByRegno(String regno); 
   
}


 