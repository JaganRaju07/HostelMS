package com.project.hostelms.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.hostelms.entity.Room;
import com.project.hostelms.entity.Visitor; 

public interface VisitorRepository extends JpaRepository<Visitor, Integer> {
    
	
	Visitor findById(int id);

	List<Visitor> findByRoom(int id);

	List<Visitor> findByStudent(int id);

	List<Visitor>  findByStudentOrderByIdAsc(int id);

	List<Visitor>  findByStudentAndStatusOrderByIdAsc(int id, int i);
 

	List<Visitor>  findByRoomInAndStatusOrderByIdAsc(List<Integer> fts, int i);

	List<Visitor> findByStatus(int i, Sort ascending);
 
   
}


 