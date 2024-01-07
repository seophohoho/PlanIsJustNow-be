package com.planisjustnow.mvc.controller;

import com.planisjustnow.mvc.entity.AuthDto;
import com.planisjustnow.mvc.entity.ResponseDto;
import com.planisjustnow.mvc.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthService authService;
    private String authEmailCode;
    @PostMapping("/auth/mail")
    public ResponseEntity<ResponseDto> orderGetAuthCode(@RequestBody AuthDto authdto){
        String result = authService.sendEmail(authdto);
        ResponseDto responseDto;
        if(result.equals("success")){
            responseDto = new ResponseDto("success",".");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
        else {
            responseDto = new ResponseDto("fail","Unexpected error");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping("/auth/check")
    public ResponseEntity<ResponseDto> orderCheckAuthCode(@RequestBody AuthDto authDto){
        String result = authService.checkAuthCode(authDto);
        if(result.equals("success")){
            ResponseDto responseDto = new ResponseDto("success",".");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
        else{
            ResponseDto responseDto = new ResponseDto("fail","Unexpected error");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}