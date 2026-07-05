package com.project.hostelms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.hostelms.entity.Block; 

public interface BlockRepository extends JpaRepository<Block, Integer> {
    
	
	Block findById(int id);

	Block findByName(String name); 
   
}


 