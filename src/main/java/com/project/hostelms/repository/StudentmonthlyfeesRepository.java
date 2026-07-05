package com.project.hostelms.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.hostelms.entity.Studentmonthlyfees; 

public interface StudentmonthlyfeesRepository extends JpaRepository<Studentmonthlyfees, Integer> {
    
	
	Studentmonthlyfees findById(int id);

	List<Studentmonthlyfees> findByUser(int id, Sort sort);

	Studentmonthlyfees findByUserAndRoomAndMonthAndYear(int student,int room, int monthValue, int year);

	List<Studentmonthlyfees> findByUserAndMonthAndYear(int student, int monthValue, int year);

	List<Studentmonthlyfees> findByMonthAndYear(int monthValue, int year);

	List<Studentmonthlyfees> findByUserAndPaid(int id, int i); 
   
}


 