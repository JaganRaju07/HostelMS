package com.project.hostelms.entity;


import java.sql.Date;
import java.sql.Timestamp;

import com.project.hostelms.LoginTypes;

import jakarta.persistence.*; 
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="registrationrequests")
public class Registrationrequests
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
 

    @Column(nullable=false)
    private String emailid;
    
    @Column(nullable=false)
    private String fullname;

    @Column(nullable=false)
    private String mobilenumber;
    
     
    @Column(nullable=false)
    private String password; 

    private String regno;
    private String gender; 
    private Date dob; 
    private int department;
    private int course; 
    private int year; 
    private String address; 
    private String parentname; 
    private String parentmobile;  
    
    private Timestamp creationdate; 
    
    private int status; 
    
  
}
