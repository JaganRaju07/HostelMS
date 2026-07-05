package com.project.hostelms.repository;

import java.util.Hashtable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.hostelms.entity.Course; 

public interface CourseRepository extends JpaRepository<Course, Integer> {
    
	
	Course findById(int id);

	Course findByName(String name);

	List<Course> findByDepartment(int department);

	 default Hashtable<Integer,String> AllAsTable()
     {
   	  List<Course> list=this.findAll();
		   Hashtable<Integer,String> map = new  Hashtable<Integer,String>(list.size());
		   for (Course i : list) map.put(i.getId(),i.getName());
		   return map;
     }
   
}


 