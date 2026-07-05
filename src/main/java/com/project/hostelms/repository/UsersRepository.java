package com.project.hostelms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.hostelms.LoginTypes;
import com.project.hostelms.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
	Users findById(int id); 
	Users findByUsername(String un);
	Users findByMobilenumber(String mobileno);
	List<Users> findByTypeOrderByFullnameAsc(String type);
	List<Users> findByTypeAndStatus(String name, int status);
	List<Users> findByFullnameContaining(String key);
	List<Users> findByIdNotInAndStatus(List<Integer> sids, int i); 
}
