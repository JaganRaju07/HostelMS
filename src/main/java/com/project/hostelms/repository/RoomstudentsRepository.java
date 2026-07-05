package com.project.hostelms.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.hostelms.entity.Roomstudents; 

public interface RoomstudentsRepository extends JpaRepository<Roomstudents, Integer> {
    
	
	Roomstudents findById(int id);

	List<Roomstudents> findByRoomOrderByIdDesc(int id);

	List<Roomstudents> findByRoomAndStatus(int id,int s); 
	 

	List<Roomstudents> findByStudentAndStatus(int id, int i);

 

	long countByStatus(int i);

	List<Roomstudents>  findByStatus(int i);

	long countByRoomAndStatus(int id, int i); 

	Roomstudents findByRoomAndStudent(int room, int student);

	List<Roomstudents>  findByStudent(int id, Sort descending); 
 
}


 