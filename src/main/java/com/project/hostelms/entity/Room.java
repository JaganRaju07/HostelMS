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
@Table(name="rooms")
public class Room
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 
    private String roomno;     
    private int block; 
    private int floor;      
    
    private int maxpersons; 
    
    private Timestamp creationtime;
    private Timestamp updationtime;
}
