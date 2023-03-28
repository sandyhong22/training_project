package com.example.backend_project.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "attendance_records")
public class AttendanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "work_record")
    private String workRecord;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name="last_modified_date")
    private LocalDateTime lastModifiedDate;
}
