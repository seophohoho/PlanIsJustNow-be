package com.planisjustnow.controller;

import com.planisjustnow.data.dto.PasswordResetSendEmailDto;
import com.planisjustnow.data.dto.PasswordResetVerificationDto;
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

    @PostMapping("send-email")
    public ResponseEntity<ResponseDto> sendEmail(@RequestBody PasswordResetSendEmailDto passwordResetSendEmailDto) {
        boolean result = passwordResetService.resetPassword(passwordResetSendEmailDto.getUserId(), passwordResetSendEmailDto.getEmail());

        if (result) {
            return new ResponseEntity<>(new ResponseDto("success", "Verification code sent successfully", null, null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDto("fail", "User not found or email not matched", null, null), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("verification")
    public ResponseEntity<ResponseDto> emailVerification(@RequestBody PasswordResetVerificationDto passwordResetVerificationDto) {
        boolean result = passwordResetService.verificationCode(passwordResetVerificationDto.getEmail(), passwordResetVerificationDto.getCode());

        if (result) {
            return new ResponseEntity<>(new ResponseDto("success", "Reset Password successfully", null, null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDto("fail", "Reset Password failed", null, null), HttpStatus.NOT_FOUND);
        }
    }

}