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
public class FailCheckInService {
    private final AttendanceRepository attendanceRepository;

    private final AttendanceMapper attendanceMapper;

    public AttendanceResponse failClockInService(UserProfileVo userProfileVo) {
        Attendance attendance = new Attendance();
        String username = userProfileVo.getUsername();
        LocalDate date = LocalDate.now();
        LocalDateTime currentTime = LocalDateTime.now();
        attendance.setUsername(username);
        attendance.setClockInTime(null);
        attendance.setDate(date);
        attendance.setCreatedDate(currentTime);
        attendanceRepository.save(attendance);

        if(attendance.getClockInTime()==null){
            log.error("username:{} ,Clock time can not be null",username);
            throw new AttendanceException("Clock time can not be null.");
        }

        return attendanceMapper.mapToResponse(username, currentTime);
    }
}
