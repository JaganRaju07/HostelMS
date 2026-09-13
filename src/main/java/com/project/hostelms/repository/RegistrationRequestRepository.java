package com.project.hostelms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.hostelms.entity.RegistrationRequest;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Integer> {
	RegistrationRequest findById(int id); 
	RegistrationRequest findByEmailid(String un);
	RegistrationRequest findByMobilenumber(String mobileno); 
	List<RegistrationRequest> findByStatusOrderByIdDesc(int s);
}
