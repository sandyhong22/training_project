package com.example.backend_project.job;

import com.example.backend_project.controller.attendance.service.AttendanceRecordsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AttendanceRecordsJob implements Job {

    private final AttendanceRecordsService attendanceRecordsService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info("attendanceRecordsJob started");
        try {
            attendanceRecordsService.executeAttendanceRecords();
        } catch (Exception e) {
            log.error("Error in AttendanceRecordsJob:{}", e.getMessage());
        }
        log.info("attendanceRecordsJob ended");
    }
}
