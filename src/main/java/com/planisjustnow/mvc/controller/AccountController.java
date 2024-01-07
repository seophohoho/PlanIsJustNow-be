package com.planisjustnow.mvc.controller;

import com.planisjustnow.mvc.entity.AccountSignUpDto;
import com.planisjustnow.mvc.entity.ResponseDto;
import com.planisjustnow.mvc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PostMapping("account/signup")
    public ResponseEntity<ResponseDto> orderSignUp(@RequestBody AccountSignUpDto accountSignUpDto){
        String result = accountService.signUp(accountSignUpDto);
        if(result.equals("success")){
            ResponseDto responseDto = new ResponseDto("success",".");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
        else if(result.equals("fail:Email is already in use")){
            ResponseDto responseDto = new ResponseDto("fail","Email is already in use");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
        else{
            ResponseDto responseDto = new ResponseDto("fail","Unexpected error");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}