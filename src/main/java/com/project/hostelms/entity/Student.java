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
@Table(name="students")
public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 
    private int user; 
    private String regno;
    private String gender; 
    private Date dob; 
    private int department;
    private int course; 
    private int year; 
    private String address; 
    private String parentname; 
    private String parentmobile; 
    private float deposit;
    private int status;   
    private Timestamp creationdate;  
}
