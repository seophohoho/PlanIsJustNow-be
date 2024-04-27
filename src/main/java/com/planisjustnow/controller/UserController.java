package com.planisjustnow.controller;

import com.planisjustnow.data.dto.ChoicePetDto;
import com.planisjustnow.data.dto.PetSignUpDto;
import com.planisjustnow.data.dto.ResponseDto;
import com.planisjustnow.service.UserPetService;
import com.planisjustnow.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserPetService userPetService;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("pet-signup")
    public ResponseEntity<ResponseDto> orderChoicePet(@RequestBody PetSignUpDto petSignUpDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String userId = jwtUtil.parseToken(httpServletRequest,httpServletResponse);
        if(userId.equals("fail:Token-not-found")){
            ResponseDto responseDto = new ResponseDto("redirect", "/", null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED);
        }
        String result = userPetService.petSignup(userId, petSignUpDto);
        if(result.equals("success")){
            ResponseDto responseDto = new ResponseDto("success",".",null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
        else{
            ResponseDto responseDto = new ResponseDto("fail","Unexpected error",null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("has-pet")
    public ResponseEntity<ResponseDto> orderIsHasPet(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
        ResponseDto responseDto;
        String userId = jwtUtil.parseToken(httpServletRequest,httpServletResponse);
        if(userId.equals("fail:Token-not-found")){
            responseDto = new ResponseDto("redirect", "/", null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED); // HTTP 상태 코드 302
        }
        Map<String, List<Object>> result = userPetService.isHasPet(userId);
        if(result.isEmpty()){
            responseDto = new ResponseDto("success","nothing",new ArrayList<>());
            return new ResponseEntity<ResponseDto>(responseDto,HttpStatus.OK);
        }
        else if(result == null){
            responseDto = new ResponseDto("fail",".",null);
            return new ResponseEntity<ResponseDto>(responseDto,HttpStatus.BAD_REQUEST);
        }
        else{
            responseDto = new ResponseDto("success","has",result.get("result"));
            return new ResponseEntity<ResponseDto>(responseDto,HttpStatus.OK);

        }
    }
    @GetMapping("all-pet-info")
    public ResponseEntity<ResponseDto> orderAllPetInfo(HttpServletResponse httpServletResponse) {
        Map<String,Object> result = userPetService.getAllPetInfo();
        if(result.get("result").equals("success")){
            ResponseDto responseDto = new ResponseDto("success","has",result.get("list"));
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
        else{
            ResponseDto responseDto = new ResponseDto("fail","Unexpected error",null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("choice-pet")
    public ResponseEntity<ResponseDto> orderChoicePet(@RequestBody ChoicePetDto choicePetDto,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String userId = jwtUtil.parseToken(httpServletRequest,httpServletResponse);
        if(userId.equals("fail:Token-not-found")){
            ResponseDto responseDto = new ResponseDto("redirect", "/", null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED);
        }
        String result = userPetService.choicePet(userId, choicePetDto);
        if(result.equals("success")){
            ResponseDto responseDto = new ResponseDto("success",".",null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
        else if(result.equals("fail:Pet not found")){
            ResponseDto responseDto = new ResponseDto("fail","Pet not found",null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
        else{
            ResponseDto responseDto = new ResponseDto("fail","Unexpected error",null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}