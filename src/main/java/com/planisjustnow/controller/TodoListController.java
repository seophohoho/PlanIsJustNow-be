package com.planisjustnow.controller;

import com.planisjustnow.data.dto.*;
import com.planisjustnow.service.TodolistService;
import com.planisjustnow.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/todolist")
public class TodoListController {
    @Autowired
    private TodolistService todolistService;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("add")
    public ResponseEntity<ResponseDto> orderAddTodolist(@RequestBody TodoListAddDto todoListAddDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        ResponseDto responseDto;
        String userId = jwtUtil.parseToken(httpServletRequest,httpServletResponse);
        if(userId.equals("fail:Token-not-found")){
            responseDto = new ResponseDto("redirect", "/", null);
            System.out.println("토큰을 발급받아라.");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED); // HTTP 상태 코드 302
        }
        Map<String,Object> result = todolistService.addTodolist(userId,todoListAddDto);
        if(result.get("result").equals("success")){
            responseDto = new ResponseDto("success",".",result.get("data"));
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
        else if(result.get("result").equals("fail")){
            responseDto = new ResponseDto("fail","Unexpected error",null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.NOT_FOUND);
        }
        return null;
    }
    @PutMapping("delete")
    public ResponseEntity<ResponseDto> orderDeleteTodolist(@RequestBody TodolistDto todolistDto,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ResponseDto responseDto;
        String userId = jwtUtil.parseToken(httpServletRequest,httpServletResponse);
        if(userId.equals("fail:Token-not-found")){
            responseDto = new ResponseDto("redirect", "/", null);
            System.out.println("토큰을 발급받아라.");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED); // HTTP 상태 코드 302
        }
        String result = todolistService.deleteTodolist(userId,todolistDto.getIdx());
        if (result.equals("success")) {
            responseDto = new ResponseDto("success", ".", null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        } else if (result.equals("fail")) {
            responseDto = new ResponseDto("fail", "Unexpected error", null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.NOT_FOUND);
        }
        return null;
    }
    @PostMapping("modify")
    public ResponseEntity<ResponseDto> orderModifyTodolist(@RequestBody TodoListUpdateDto todoListUpdateDto,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        ResponseDto responseDto;
        String userId = jwtUtil.parseToken(httpServletRequest,httpServletResponse);
        if(userId.equals("fail:Token-not-found")){
            responseDto = new ResponseDto("redirect", "/", null);
            System.out.println("토큰을 발급받아라.");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED); // HTTP 상태 코드 302
        }
        String result = todolistService.modifyTodolist(userId,todoListUpdateDto);
        if(result.equals("success")){
            responseDto = new ResponseDto("success", ".", null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        } else if(result.equals("fail")){
            responseDto = new ResponseDto("fail", "Unexpected error", null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.NOT_FOUND);
        }
        return null;
    }
    @PostMapping("complete")
    public ResponseEntity<ResponseDto> orderCompleteTodolist(@RequestBody TodolistDto todolistDto,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        ResponseDto responseDto;
        String userId = jwtUtil.parseToken(httpServletRequest,httpServletResponse);
        if(userId.equals("fail:Token-not-found")){
            responseDto = new ResponseDto("redirect", "/", null);
            System.out.println("토큰을 발급받아라.");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED); // HTTP 상태 코드 302
        }
        String result = todolistService.completeTodolist(userId,todolistDto.getIdx());
        if(result.equals("success")){
            responseDto = new ResponseDto("success",".",null);
            return new ResponseEntity<ResponseDto>(responseDto,HttpStatus.OK);
        }
        else if(result.equals("fail")){
            responseDto = new ResponseDto("fail", "Unexpected error", null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.NOT_FOUND);
        }
        return null;
    }
    @GetMapping("select")
    public ResponseEntity<?> orderSelectTodolist(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        ResponseDto responseDto;
        String userId = jwtUtil.parseToken(httpServletRequest,httpServletResponse);
        if(userId.equals("fail:Token-not-found")){
            responseDto = new ResponseDto("redirect", "/", null);
            System.out.println("토큰을 발급받아라.");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED); // HTTP 상태 코드 302
        }
        Map<String, List<Map<String, Object>>> tasks = todolistService.selectTodolist(userId);
        if (tasks.isEmpty()) {
            responseDto = new ResponseDto("success","empty",new ArrayList<>());
            return new ResponseEntity<ResponseDto>(responseDto,HttpStatus.OK);
        }
        else if(tasks == null){
            responseDto = new ResponseDto("fail", "Unexpected error", null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.NOT_FOUND);
        }
        else{
            responseDto = new ResponseDto("success", ".", tasks);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
    }
}
