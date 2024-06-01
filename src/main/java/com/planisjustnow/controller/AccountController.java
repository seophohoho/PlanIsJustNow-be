package com.planisjustnow.controller;

import com.planisjustnow.config.S3Config;
import com.planisjustnow.data.dto.AccountSignInDto;
import com.planisjustnow.data.dto.AccountSignUpDto;
import com.planisjustnow.data.dto.ResponseDto;
import com.planisjustnow.service.AccountService;
import com.planisjustnow.service.S3Service;
import com.planisjustnow.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private S3Service s3Service;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("signup")
    public ResponseEntity<ResponseDto> orderSignUp(@RequestPart("email") String email,@RequestPart("password") String password,@RequestPart("nickname") String nickname,@RequestPart("file") MultipartFile file) throws IOException {
        String profileUrl = s3Service.saveFile(file,"profile/");
        String result = accountService.signUp(email,password,nickname,profileUrl);

        if(result.equals("success")){
            ResponseDto responseDto = new ResponseDto("success",".",null,null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
        else if(result.equals("fail:Email is already in use")){
            ResponseDto responseDto = new ResponseDto("fail","Email is already in use",null,null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.CONFLICT);
        }
        else{
            ResponseDto responseDto = new ResponseDto("fail","Unexpected error",null,null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("signin")
    public ResponseEntity<ResponseDto> orderSignIn(@RequestBody AccountSignInDto accountSignInDto, HttpServletResponse httpServletResponse){
        String result = accountService.signIn(accountSignInDto,httpServletResponse);
        if(result.equals("success")){
            ResponseDto responseDto = new ResponseDto("success",".",null,null);
            jwtUtil.createToken(accountSignInDto.getEmail(),httpServletResponse);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
        else if(result.equals("false:Not matche")){
            ResponseDto responseDto = new ResponseDto("fail","Not matched error",null,null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
        else{
            ResponseDto responseDto = new ResponseDto("fail","Unexpected error",null,null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("logout")
    public ResponseEntity<ResponseDto> orderLogout(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest){
        try{
            jwtUtil.logout(httpServletResponse);
            ResponseDto responseDto = new ResponseDto("success",".",null,null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }catch (Exception e){
            ResponseDto responseDto = new ResponseDto("fail","Unexpected error",null,null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}