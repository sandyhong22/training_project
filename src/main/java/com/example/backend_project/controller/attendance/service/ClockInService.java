package com.example.backend_project.controller.attendance.service;

import com.example.backend_project.controller.attendance.dto.reponse.AttendanceResponse;
import com.example.backend_project.dto.UserProfileVo;
import com.example.backend_project.entity.Attendance;
import com.example.backend_project.expection.AttendanceException;
import com.example.backend_project.mapper.AttendanceMapper;
import com.example.backend_project.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClockInService {

    private final AttendanceRepository attendanceRepository;

    private final AttendanceMapper attendanceMapper;

    public AttendanceResponse clockInService(UserProfileVo userProfileVo) {
        LocalDate date = LocalDate.now();
        if (attendanceRepository.existByUsernameAndDate(userProfileVo.getUsername(), date).isPresent()) {
            throw new AttendanceException("Today was already clock in");
        }
        Attendance attendance = new Attendance();
        String username = userProfileVo.getUsername();
        LocalDateTime clockInTime = LocalDateTime.now();
        attendance.setUsername(username);
        attendance.setClockInTime(clockInTime);
        attendance.setDate(date);
        attendanceRepository.save(attendance);

        return attendanceMapper.mapToResponse(username, clockInTime);
    }

}
