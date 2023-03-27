package com.example.backend_project.repository;


import com.example.backend_project.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRecordsRepository extends JpaRepository<AttendanceRecord, Long> {
}
