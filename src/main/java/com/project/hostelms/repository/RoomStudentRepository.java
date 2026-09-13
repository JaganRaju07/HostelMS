package com.project.hostelms.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.hostelms.entity.RoomStudent; 

public interface RoomStudentRepository extends JpaRepository<RoomStudent, Integer> {
	RoomStudent findById(int id);
	List<RoomStudent> findByRoomOrderByIdDesc(int id);
	List<RoomStudent> findByRoomAndStatus(int id,int s); 
	List<RoomStudent> findByStudentAndStatus(int id, int i);
	long countByStatus(int i);
	List<RoomStudent>  findByStatus(int i);
	long countByRoomAndStatus(int id, int i); 
	RoomStudent findByRoomAndStudent(int room, int student);
	List<RoomStudent>  findByStudent(int id, Sort descending); 
}
