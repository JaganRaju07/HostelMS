package com.project.hostelms.entity;


import java.sql.Timestamp;

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
@Table(name="complaint")
public class Complaint
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
 
    private String ctitle; 
       
    private String cdescription; 
     
    private int student;  
    
    private int room; 
    
    private int status; 
     
    private Timestamp creationdate;
}
