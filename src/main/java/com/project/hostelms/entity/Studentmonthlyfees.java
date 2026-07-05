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
@Table(name="student_monthlyfees")
public class Studentmonthlyfees
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 
    private int user;  
    private int room;
    private int  month; 
    private int year; 
    private int days;
    private float rent; 
    private float mess; 
    private float other; 
    private float totalfees; 
    private int paid; 
    private Timestamp paiddate; 
    private String paidby; 
    private Timestamp creationtime;
}
