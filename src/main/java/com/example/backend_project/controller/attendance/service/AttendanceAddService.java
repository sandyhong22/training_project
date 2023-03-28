package com.example.backend_project.controller.attendance.service;

import com.example.backend_project.controller.attendance.dto.reponse.AttendanceResponse;
import com.example.backend_project.controller.attendance.dto.request.AttendanceAddRequest;
import com.example.backend_project.enums.AttendanceType;
import com.example.backend_project.expection.AttendanceException;
import com.example.backend_project.mapper.AttendanceMapper;
import com.example.backend_project.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceAddService {

    private final AttendanceRepository attendanceRepository;

    public String attendanceAdd(AttendanceAddRequest attendanceAddRequest) {
        log.info("sa{}",attendanceAddRequest);
        AttendanceType attendanceType = attendanceAddRequest.getAttendanceType();
        switch (attendanceType) {
            case CLOCK_IN:
                attendanceRepository.updateClockInTime(attendanceAddRequest.getTime(), attendanceAddRequest.getUsername(), attendanceAddRequest.getDate());
                return "Successfully made up for missed check-in.";
            case CLOCK_OUT:
                attendanceRepository.updateClockOutTime(attendanceAddRequest.getTime(), attendanceAddRequest.getUsername(), attendanceAddRequest.getDate());
                return "Successfully made up for missed check-out.";
            default:
                throw new AttendanceException("Error Attendance Type");
        }
    }
}
