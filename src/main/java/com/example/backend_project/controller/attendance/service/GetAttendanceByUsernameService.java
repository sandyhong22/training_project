package com.example.backend_project.controller.attendance.service;

import com.example.backend_project.controller.attendance.dto.request.AttendanceRequest;
import com.example.backend_project.controller.user.service.LoginService;
import com.example.backend_project.entity.Attendance;
import com.example.backend_project.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetAttendanceByUsernameService {

    private final AttendanceRepository attendanceRepository;

    public List<Attendance> getAttendanceByUsernameAndDate(AttendanceRequest attendanceRequest) {
        return attendanceRepository.findAllByUsernameAndDate(attendanceRequest.getUsernames(), attendanceRequest.getDate());
    }
}
