package com.example.backend_project.controller.attendance.dto.reponse;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AttendanceTotalResponse {
    private String username;

    private LocalDate date;

    private String workRecords;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
