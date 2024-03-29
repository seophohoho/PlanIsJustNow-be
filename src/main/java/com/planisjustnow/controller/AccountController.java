package com.planisjustnow.controller;

import com.planisjustnow.data.dto.AccountSignInDto;
import com.planisjustnow.data.dto.AccountSignUpDto;
import com.planisjustnow.data.dto.ResponseDto;
import com.planisjustnow.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PostMapping("signup")
    public ResponseEntity<ResponseDto> orderSignUp(@RequestBody AccountSignUpDto accountSignUpDto){
        String result = accountService.signUp(accountSignUpDto);
        if(result.equals("success")){
            ResponseDto responseDto = new ResponseDto("success",".",null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
        else if(result.equals("fail:Email is already in use")){
            ResponseDto responseDto = new ResponseDto("fail","Email is already in use",null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.CONFLICT);
        }
        else{
            ResponseDto responseDto = new ResponseDto("fail","Unexpected error",null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("signin")
    public ResponseEntity<ResponseDto> orderSignIn(@RequestBody AccountSignInDto accountSignInDto){
        String result = accountService.signIn(accountSignInDto);
        if(result.equals("success")){
            ResponseDto responseDto = new ResponseDto("success",".",null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
        else if(result.equals("not matche")){
            ResponseDto responseDto = new ResponseDto("fail","Unexpected error",null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
        else{
            ResponseDto responseDto = new ResponseDto("fail","Unexpected error",null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}