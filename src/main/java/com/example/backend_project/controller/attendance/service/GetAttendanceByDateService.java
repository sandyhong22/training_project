package com.example.backend_project.controller.attendance.service;

import com.example.backend_project.controller.attendance.dto.reponse.AttendanceTotalResponse;
import com.example.backend_project.entity.AttendanceRecord;
import com.example.backend_project.mapper.AttendanceMapper;
import com.example.backend_project.repository.AttendanceRecordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAttendanceByDateService {
    private final AttendanceRecordsRepository attendanceRecordsRepository;

    private final AttendanceMapper attendanceMapper;

    public List<AttendanceTotalResponse> getAttendanceByDate(LocalDate date) {
        List<AttendanceRecord> attendanceRecords = attendanceRecordsRepository.findAllByDate(date);
        List<AttendanceTotalResponse> attendanceTotalResponseList = new ArrayList<>();

        for (AttendanceRecord attendanceRecord : attendanceRecords) {
            AttendanceTotalResponse attendanceTotalResponse = attendanceMapper.mapToResponse(attendanceRecord);
            attendanceTotalResponseList.add(attendanceTotalResponse);
        }
        return attendanceTotalResponseList;
    }

}
