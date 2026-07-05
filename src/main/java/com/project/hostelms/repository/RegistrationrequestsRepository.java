package com.project.hostelms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
 
import com.project.hostelms.entity.Registrationrequests;

public interface RegistrationrequestsRepository extends JpaRepository<Registrationrequests, Integer> {
	Registrationrequests findById(int id); 
	Registrationrequests findByEmailid(String un);
	Registrationrequests findByMobilenumber(String mobileno); 
	List<Registrationrequests> findByStatusOrderByIdDesc(int s);
}
