package com.example.backend_project.controller.attendance;

import com.example.backend_project.controller.attendance.dto.reponse.AttendanceResponse;
import com.example.backend_project.controller.attendance.dto.reponse.AttendanceTotalResponse;
import com.example.backend_project.controller.attendance.service.AttendanceRecordsService;
import com.example.backend_project.controller.attendance.service.ClockInService;
import com.example.backend_project.controller.attendance.service.ClockOutService;
import com.example.backend_project.controller.attendance.service.GetAttendanceByUsernameService;
import com.example.backend_project.dto.ResponseDto;
import com.example.backend_project.dto.UserDto;
import com.example.backend_project.dto.UserProfileVo;
import com.example.backend_project.entity.Attendance;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "打卡相關Api")
@RequestMapping("/attendance")
public class AttendanceController {

    private final ClockInService clockInService;

    private final ClockOutService clockOutService;

    private final AttendanceRecordsService attendanceRecordsService;

    private final GetAttendanceByUsernameService getAttendanceByUsernameService;


    @GetMapping()
    public ResponseDto<List<AttendanceTotalResponse>> attendance() {
        return ResponseDto.success(attendanceRecordsService.getAttendanceRecords());
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

    @GetMapping("/{username}")
    public List<Attendance> getAttendanceByUsername(@PathVariable("username") String username) {
        return getAttendanceByUsernameService.getAttendanceByUsername(username);
    }
}
