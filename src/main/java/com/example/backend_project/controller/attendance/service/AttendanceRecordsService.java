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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceRecordsService {

    private static final Executor EXECUTOR = Executors.newFixedThreadPool(8);

    private final AttendanceRepository attendanceRepository;

    private final AttendanceRecordsRepository attendanceRecordsRepository;

    private final AttendanceMapper attendanceMapper;

    public List<AttendanceTotalResponse> getAttendanceRecords() throws ExecutionException, InterruptedException {
        List<Attendance> todayAttendanceList = attendanceRepository.findAllByDate(LocalDate.now().minusDays(1));
        List<AttendanceRecord> attendanceRecordList = attendanceRecord(todayAttendanceList).get();
        attendanceRecordsRepository.saveAll(attendanceRecordList);
        return attendanceMapper.mapToResponse(attendanceRecordList);

    }

    private String checkAttendanceTime(Attendance attendance) {

        if (attendance.getClockOutTime() != null && attendance.getClockInTime() != null) {
            Duration duration = Duration.between(attendance.getClockInTime(), attendance.getClockOutTime());
            return DurationFormatUtils.formatDuration(duration.toMillis(), "HH:mm:ss");
        } else {
            return null;
        }
    }

    public AttendanceRecord setAttendance(Attendance attendance) {

        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.setWorkRecord(checkAttendanceTime(attendance));
        attendanceRecord.setDate(attendance.getDate());
        attendanceRecord.setUsername(attendance.getUsername());
        attendanceRecord.setCreatedDate(LocalDateTime.now());
        return attendanceRecord;
    }

    public CompletableFuture<List<AttendanceRecord>> attendanceRecord(List<Attendance> todayAttendanceList) {
        log.info("Enter in CompletableFuture");

        CompletableFuture<List<AttendanceRecord>> attendanceRecordsListEnterCompletableFuture = new CompletableFuture<>();
        List<AttendanceRecord> attendanceRecordList = new ArrayList<>();
        for (Attendance attendance : todayAttendanceList) {
            attendanceRecordsListEnterCompletableFuture = CompletableFuture.supplyAsync(() -> {
                log.info("CompletableFuture{},", attendance);
                AttendanceRecord attendanceRecord = setAttendance(attendance);
                attendanceRecordList.add(attendanceRecord);
                return attendanceRecordList;

            }, EXECUTOR);
        }
        CompletableFuture.allOf(attendanceRecordsListEnterCompletableFuture)
                .thenRun(() -> log.info("Task completed"))
                .join();

        return attendanceRecordsListEnterCompletableFuture;
    }


}
