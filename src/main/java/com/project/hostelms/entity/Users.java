package com.project.hostelms.entity;


import java.sql.Timestamp;
import java.util.List;

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
@Table(name="users")
public class  Users 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
 

    @Column(nullable=false)
    private String username;
    
    @Column(nullable=false)
    private String fullname;

    @Column(nullable=false)
    private String mobilenumber;
    
     
    @Column(nullable=false)
    private String password; 
   
    private String type; 

    
    private Timestamp creationdate;
    private Timestamp updationdate;
    
    private int status; 
    
  
}
