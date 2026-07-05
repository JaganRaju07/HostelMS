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
@Table(name="settings")
public class Setting
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
 

    @Column(nullable=false)
    private String skey;
    
    @Column(nullable=false)
    private String sdesc;

    @Column(nullable=false)
    private String sval;
    
       
    private Timestamp updationdate; 
}
