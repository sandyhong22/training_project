package com.example.backend_project.controller.attendance.service;

import com.example.backend_project.entity.Attendance;
import com.example.backend_project.entity.AttendanceRecord;
import com.example.backend_project.mapper.AttendanceMapper;
import com.example.backend_project.repository.AttendanceRecordsRepository;
import com.example.backend_project.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public void executeAttendanceRecords() throws ExecutionException, InterruptedException {
        List<Attendance> todayAttendanceList = attendanceRepository.findAllByDate(LocalDate.now().minusDays(1));
        List<AttendanceRecord> attendanceRecordList = attendanceRecord(todayAttendanceList).get();
        attendanceRecordsRepository.saveAll(attendanceRecordList);
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
        log.info("Thread Task start");

        List<CompletableFuture<AttendanceRecord>> attendanceRecordFutures = new ArrayList<>();
        for (Attendance attendance : todayAttendanceList) {
            CompletableFuture<AttendanceRecord> attendanceRecordFuture = CompletableFuture.supplyAsync(() -> setAttendance(attendance), EXECUTOR);
            attendanceRecordFutures.add(attendanceRecordFuture);
        }

        CompletableFuture<List<AttendanceRecord>> allAttendanceRecordsFuture = CompletableFuture.allOf(attendanceRecordFutures.toArray(new CompletableFuture[0]))
                .thenApply(v -> {
                    List<AttendanceRecord> attendanceRecordsList = new ArrayList<>();
                    for (CompletableFuture<AttendanceRecord> future : attendanceRecordFutures) {
                        attendanceRecordsList.add(future.join());
                    }
                    return attendanceRecordsList;
                });

        allAttendanceRecordsFuture.thenRun(() -> log.info("Thread Task completed"));


        return allAttendanceRecordsFuture;
    }
}
