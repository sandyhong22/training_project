package com.example.backend_project.controller.user;

import com.example.backend_project.controller.user.dto.reponse.UserResponse;
import com.example.backend_project.controller.user.dto.request.ResetPasswordRequest;
import com.example.backend_project.controller.user.service.FindAllUserService;
import com.example.backend_project.controller.user.service.ResetPasswordService;
import com.example.backend_project.controller.user.service.UserFindByUsernameService;
import com.example.backend_project.dto.ResponseDto;
import com.example.backend_project.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Valid
@Tag(name = "帳戶相關Api")
@RequestMapping("/account")
public class UserController {
    private final FindAllUserService accountService;
    
    private final UserFindByUsernameService userFindByUsername;
    
    private final ResetPasswordService resetPasswordService;
    
    @Operation(summary = "找出所有帳戶")
    @GetMapping()
    public ResponseDto<List<User>> getAllAccount() {
        return ResponseDto.success(accountService.getAllAccount());
    }
    
    @Operation(summary = "找尋帳戶名稱")
    @GetMapping("/username/{username}")
    public ResponseDto<UserResponse> findByUsername(@PathVariable("username") String username) {
        return ResponseDto.success(userFindByUsername.findByUsername(username));
    }
    
    @Operation(summary = "重設密碼")
    @PostMapping("/resetPassword")
    public ResponseDto<String> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return ResponseDto.success(resetPasswordService.resetPassword(resetPasswordRequest));
    }
    
}
