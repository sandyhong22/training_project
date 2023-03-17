package com.example.backend_project.controller.user;

import com.example.backend_project.controller.user.dto.reponse.UserResponse;
import com.example.backend_project.controller.user.dto.request.ResetPasswordRequest;
import com.example.backend_project.controller.user.service.*;
import com.example.backend_project.dto.ResponseDto;
import com.example.backend_project.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Valid
@RequestMapping("/account")
public class UserController {
    private final FindAllUserService accountService;
    
    private final UserFindByUsernameService userFindByUsername;
    
    private final ResetPasswordService resetPasswordService;
    
    @GetMapping()
    public ResponseDto<List<User>> getAllAccount() {
        return ResponseDto.success(accountService.getAllAccount());
    }
    
    
    @GetMapping("/username/{username}")
    public ResponseDto<UserResponse> findByUsername(@PathVariable("username") String username) {
        return ResponseDto.success(userFindByUsername.findByUsername(username));
    }
    
    @PostMapping("/resetPassword")
    public ResponseDto<String> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
        return ResponseDto.success(resetPasswordService.resetPassword(resetPasswordRequest));
    }
    
}
