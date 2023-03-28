package com.example.backend_project.config;

import com.example.backend_project.job.AttendanceRecordsJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class QuartzConfig {


    @Value("${spring.cron.expression.attendance.records}")
    private String attendanceRecordsSchedule;

    @Bean
    public JobDetail attendanceRecordsJobDetail() {
        return JobBuilder.newJob(AttendanceRecordsJob.class)
                .withIdentity("attendanceRecords")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger attendanceRecordsTrigger(JobDetail attendanceRecordsJobDetail) {

        log.info("attendanceRecordsJobDetail: {}", attendanceRecordsJobDetail);

        return TriggerBuilder.newTrigger()
                .forJob(attendanceRecordsJobDetail)
                .withIdentity("attendanceRecordsTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(attendanceRecordsSchedule))
                .build();
    }

}
