package com.example.backend_project.controller.attendance.dto.request;

import com.example.backend_project.enums.AttendanceType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AttendanceAddRequest {
    private String username;

    private AttendanceType attendanceType;

    private LocalDate date;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime time;


}
