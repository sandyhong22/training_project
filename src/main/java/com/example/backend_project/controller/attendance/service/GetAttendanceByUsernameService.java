package com.example.backend_project.controller.attendance.service;

import com.example.backend_project.entity.Attendance;
import com.example.backend_project.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAttendanceByUsernameService {

    private final AttendanceRepository attendanceRepository;

    public List<Attendance> getAttendanceByUsername(String username) {
        return attendanceRepository.findAllByUsername(username);
    }
}
