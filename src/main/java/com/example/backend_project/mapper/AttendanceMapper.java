package com.example.backend_project.mapper;

import com.example.backend_project.controller.attendance.dto.reponse.AttendanceResponse;
import com.example.backend_project.controller.attendance.dto.reponse.AttendanceTotalResponse;
import com.example.backend_project.entity.Attendance;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;


@Component
public class AttendanceMapper {

    public AttendanceResponse mapToResponse(String username, LocalDateTime time) {
        AttendanceResponse attendanceResponse = new AttendanceResponse();
        attendanceResponse.setTime(time);
        attendanceResponse.setUsername(username);
        return attendanceResponse;
    }

    public AttendanceTotalResponse mapToResponse(Attendance attendance, String workHours) {
        AttendanceTotalResponse attendanceTotalResponse = new AttendanceTotalResponse();
        attendanceTotalResponse.setUsername(attendance.getUsername());
        attendanceTotalResponse.setDate(attendance.getDate());
        attendanceTotalResponse.setWorkRecords(workHours);
        return attendanceTotalResponse;
    }

}


