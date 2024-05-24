package com.planisjustnow.controller;

import com.planisjustnow.data.dto.PasswordResetDto;
import com.planisjustnow.data.dto.ResponseDto;
import com.planisjustnow.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/password-reset")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping
    public ResponseEntity<ResponseDto> requestPasswordReset(@RequestBody PasswordResetDto passwordResetDto) {

        //String result = passwordResetService.resetPassword("test", "ljw00391@gmail.com"); //테스트
        boolean result = passwordResetService.resetPassword(passwordResetDto.getUserId(), passwordResetDto.getEmail());

        if (result) {
            return new ResponseEntity<>(new ResponseDto("success", "Verification code sent successfully", null, null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDto("fail", "User not found or email not matched", null, null), HttpStatus.NOT_FOUND);
        }
    }

}