package com.example.backend_project.controller.attendance.dto.reponse;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceResponse {

    private String username;

    private LocalDateTime time;
}
