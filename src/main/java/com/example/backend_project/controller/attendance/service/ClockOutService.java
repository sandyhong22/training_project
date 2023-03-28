package com.example.backend_project.controller.attendance.service;

import com.example.backend_project.controller.attendance.dto.reponse.AttendanceResponse;
import com.example.backend_project.dto.UserProfileVo;
import com.example.backend_project.expection.AttendanceException;
import com.example.backend_project.mapper.AttendanceMapper;
import com.example.backend_project.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClockOutService {
    private final AttendanceRepository attendanceRepository;

    private final AttendanceMapper attendanceMapper;

    public AttendanceResponse clockOutService(UserProfileVo userProfileVo) {

        String username = userProfileVo.getUsername();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDate date = LocalDate.now();

        if (attendanceRepository.existClockOutTimeByUsernameAndDate(username, date).isPresent()) {
            throw new AttendanceException("Today was already clock out");
        }

        attendanceRepository.clockOut(currentTime, username, date);

        return attendanceMapper.mapToResponse(username, currentTime);
    }
}
