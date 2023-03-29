package com.example.backend_project.controller.attendance;

import com.example.backend_project.controller.attendance.dto.reponse.AttendanceResponse;
import com.example.backend_project.controller.attendance.dto.reponse.AttendanceTotalResponse;
import com.example.backend_project.controller.attendance.dto.request.AttendanceAddRequest;
import com.example.backend_project.controller.attendance.dto.request.AttendanceRequest;
import com.example.backend_project.controller.attendance.service.*;
import com.example.backend_project.dto.ResponseDto;
import com.example.backend_project.dto.UserDto;
import com.example.backend_project.dto.UserProfileVo;
import com.example.backend_project.entity.Attendance;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "打卡相關Api")
@RequestMapping("/attendance")
public class AttendanceController {

    private final ClockInService clockInService;

    private final ClockOutService clockOutService;

    private final AttendanceAddService attendanceAddService;

    private final GetAttendanceByUsernameService getAttendanceByUsernameService;

    private final GetAttendanceByDateService getAttendanceByDateService;

    private final AttendanceRecordsService attendanceRecordsService;


    @GetMapping("records/")
    public ResponseDto<List<AttendanceTotalResponse>> attendanceByDate(@RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseDto.success(getAttendanceByDateService.getAttendanceByDate(date));
    }

    @PostMapping("/clockIn")
    public ResponseDto<AttendanceResponse> clockIn(@AuthenticationPrincipal UserDto user,
                                                   @Parameter(hidden = true) @RequestHeader(name = HttpHeaders.AUTHORIZATION) String bearerToken) {
        UserProfileVo userProfileVo = new UserProfileVo();
        userProfileVo.setName(user.getName());
        userProfileVo.setEmail(user.getEmail());
        userProfileVo.setUsername(user.getUsername());
        userProfileVo.setBearerToken(bearerToken);
        return ResponseDto.success(clockInService.clockInService(userProfileVo));
    }

    @PostMapping("/clockOut")
    public ResponseDto<AttendanceResponse> clockOut(@AuthenticationPrincipal UserDto user,
                                                    @Parameter(hidden = true) @RequestHeader(name = HttpHeaders.AUTHORIZATION) String bearerToken) {
        UserProfileVo userProfileVo = new UserProfileVo();
        userProfileVo.setName(user.getName());
        userProfileVo.setEmail(user.getEmail());
        userProfileVo.setUsername(user.getUsername());
        userProfileVo.setBearerToken(bearerToken);
        return ResponseDto.success(clockOutService.clockOutService(userProfileVo));
    }

    @PostMapping("/attendanceAdd")
    public ResponseDto<String> attendanceAdd(@RequestBody AttendanceAddRequest attendanceAddRequest) {
        return ResponseDto.success(attendanceAddService.attendanceAdd(attendanceAddRequest));
    }

    @GetMapping("/getByUsernameAndDate")
    public ResponseDto<List<Attendance>> getAttendanceByUsername(@RequestBody AttendanceRequest attendanceRequest) {
        return ResponseDto.success(getAttendanceByUsernameService.getAttendanceByUsernameAndDate(attendanceRequest));
    }
}
