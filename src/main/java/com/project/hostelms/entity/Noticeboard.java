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
@Table(name="noticeboard")
public class Noticeboard
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 
 
    private String subject; 
       
    private String message; 
     
    
    private int status; 
     
    private Timestamp creationdate;
    private Timestamp updationdate;
}
