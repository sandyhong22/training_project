package com.example.backend_project.repository;


import com.example.backend_project.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRecordsRepository extends JpaRepository<AttendanceRecord, Long> {

    List<AttendanceRecord> findAllByDate(LocalDate date);
}
