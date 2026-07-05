package com.project.hostelms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.hostelms.entity.Room; 

public interface RoomRepository extends JpaRepository<Room, Integer> {
    
	
	Room findById(int id);

	List<Room> findByBlock(int id); 

	Room findByBlockAndRoomno(int block, String roomno);

	Room findByRoomno(String roomno); 
   
}


 