package com.project.hostelms.repository;
 

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.hostelms.entity.Setting;

public interface SettingsRepository extends JpaRepository<Setting, Integer> {
	Setting findById(int id); 
	Setting findBySkey(String key); 
}
