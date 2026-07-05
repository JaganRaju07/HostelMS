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
@Table(name="visitors")
public class Visitor
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int student; 
    
    private int room; 
    
    private String visitorname;
    
    private String purpose; 
    private String visitingtime;
     
    
    private Timestamp creationtime; 
    
    private int status;
 
}
