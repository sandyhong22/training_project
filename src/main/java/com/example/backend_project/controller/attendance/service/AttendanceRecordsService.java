package com.example.backend_project.controller.attendance.service;

import com.example.backend_project.controller.attendance.dto.reponse.AttendanceTotalResponse;
import com.example.backend_project.entity.Attendance;
import com.example.backend_project.entity.AttendanceRecord;
import com.example.backend_project.mapper.AttendanceMapper;
import com.example.backend_project.repository.AttendanceRecordsRepository;
import com.example.backend_project.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceRecordsService {

    private final AttendanceRepository attendanceRepository;

    private final AttendanceRecordsRepository attendanceRecordsRepository;

    private final AttendanceMapper attendanceMapper;

    public List<AttendanceTotalResponse> getAttendanceRecords() throws InterruptedException, ExecutionException {
        List<Attendance> todayAttendanceList = attendanceRepository.findAllByDate(LocalDate.now());
        List<AttendanceTotalResponse> attendanceTotalResponseList = new ArrayList<>();
        List<AttendanceRecord> attendanceRecordList = new ArrayList<>();
        for (Attendance attendance : todayAttendanceList) {
            AttendanceRecord attendanceRecord = new AttendanceRecord();
            attendanceRecord.setWorkRecord(checkAttendanceTime(attendance));
            attendanceRecord.setDate(attendance.getDate());
            attendanceRecord.setUsername(attendance.getUsername());
            attendanceRecord.setCreatedDate(LocalDateTime.now());
            attendanceRecordList.add(attendanceRecord);

            attendanceTotalResponseList.add(attendanceMapper.mapToResponse(attendanceRecord));

        }
        attendanceRecordsRepository.saveAll(attendanceRecordList);
        return attendanceTotalResponseList;

    }

    private String checkAttendanceTime(Attendance attendance) {
        if (attendance.getClockOutTime() != null && attendance.getClockInTime() != null) {
            Duration duration = Duration.between(attendance.getClockInTime(), attendance.getClockOutTime());
            return DurationFormatUtils.formatDuration(duration.toMillis(), "HH:mm:ss");
        } else {
            return null;
        }

    }

}
