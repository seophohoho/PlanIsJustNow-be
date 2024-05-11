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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
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
            ResponseDto responseDto = new ResponseDto("redirect", "/", null,null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED);
        }
        String result = userPetService.petSignup(userId, petSignUpDto);
        if(result.equals("success")){
            ResponseDto responseDto = new ResponseDto("success",".",null,null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
        else{
            ResponseDto responseDto = new ResponseDto("fail","Unexpected error",null,null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("has-pet")
    public ResponseEntity<ResponseDto> orderIsHasPet(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
        ResponseDto responseDto;
        String userId = jwtUtil.parseToken(httpServletRequest,httpServletResponse);
        if(userId.equals("fail:Token-not-found") || userId == null){
            responseDto = new ResponseDto("redirect", "/", null,null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED); // HTTP 상태 코드 302
        }
        Map<String, Object> result = userPetService.isHasPet(userId);
        List<?> resultList = (List<?>) result.get("result");
        if(resultList.isEmpty()){
            responseDto = new ResponseDto("success","nothing",new ArrayList<>(),result.get("userInfo"));
            return new ResponseEntity<ResponseDto>(responseDto,HttpStatus.OK);
        }
        else if(result == null){
            responseDto = new ResponseDto("fail",".",null,null);
            return new ResponseEntity<ResponseDto>(responseDto,HttpStatus.BAD_REQUEST);
        }
        else{
            responseDto = new ResponseDto("success","has",result.get("result"),result.get("userInfo"));
            return new ResponseEntity<ResponseDto>(responseDto,HttpStatus.OK);
        }
    }
    @GetMapping("all-pet-info")
    public ResponseEntity<ResponseDto> orderAllPetInfo(HttpServletResponse httpServletResponse) {
        Map<String,Object> result = userPetService.getAllPetInfo();
        if(result.get("result").equals("success")){
            ResponseDto responseDto = new ResponseDto("success","has",result.get("list"),null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
        else{
            ResponseDto responseDto = new ResponseDto("fail","Unexpected error",null,null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("choice-pet")
    public ResponseEntity<ResponseDto> orderChoicePet(@RequestBody ChoicePetDto choicePetDto,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String userId = jwtUtil.parseToken(httpServletRequest,httpServletResponse);
        if(userId.equals("fail:Token-not-found")){
            ResponseDto responseDto = new ResponseDto("redirect", "/", null,null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED);
        }
        String result = userPetService.choicePet(userId, choicePetDto);
        if(result.equals("success")){
            ResponseDto responseDto = new ResponseDto("success",".",null,null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
        else if(result.equals("fail:Pet not found")){
            ResponseDto responseDto = new ResponseDto("fail","Pet not found",null,null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
        else{
            ResponseDto responseDto = new ResponseDto("fail","Unexpected error",null,null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("interaction")
    public ResponseEntity<ResponseDto> orderInteraction(@RequestParam String id,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String userId = jwtUtil.parseToken(httpServletRequest,httpServletResponse);
        ResponseDto responseDto;
        if(userId.equals("fail:Token-not-found")){
            responseDto = new ResponseDto("redirect", "/", null,null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED);
        }
        String[] result = userPetService.doInteraction(userId,id).split("\\s+");
        if(result[0].equals("success")){
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("evol",Integer.parseInt(result[2]));
            resultMap.put("friendship",Integer.parseInt(result[1]));
            responseDto = new ResponseDto("success",".",resultMap,null);
            return new ResponseEntity<ResponseDto>(responseDto,HttpStatus.OK);
        }
        else{
            responseDto = new ResponseDto("fail","Unexpected error",null,null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}