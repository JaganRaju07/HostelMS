package com.project.hostelms.entity;

import java.sql.Date;
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
@Table(name="room_students")
public class RoomStudent
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int room; 
    private int student;
    private Date fromdt;
    private Date todt;
    private float monthlyrent;
    private int status;
    private Timestamp creationtime;
    private Timestamp updationtime;
}
