package com.planisjustnow.controller;

import com.planisjustnow.data.dto.*;
import com.planisjustnow.service.TodolistService;
import com.planisjustnow.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    private JwtUtil jwt;
    @PostMapping("add")
    public ResponseEntity<ResponseDto> orderAddTodolist(@RequestBody TodoListAddDto todoListAddDto, HttpServletRequest httpServletRequest){
        ResponseDto responseDto;
        jwt.parseToken(httpServletRequest);
        String result = todolistService.addTodolist(todoListAddDto);
        if(result.equals("success")){
            responseDto = new ResponseDto("success",".",null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
        else if(result.equals("fail")){
            responseDto = new ResponseDto("fail",".",null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.NOT_FOUND);
        }
        return null;
    }
    @PutMapping("delete")
    public ResponseEntity<ResponseDto> orderDeleteTodolist(@RequestBody TodolistDto todolistDto) {
        ResponseDto responseDto;
        String result = todolistService.deleteTodolist(todolistDto.getIdx());
        if (result.equals("success")) {
            responseDto = new ResponseDto("success", ".", null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        } else if (result.equals("fail")) {
            responseDto = new ResponseDto("fail", ".", null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.NOT_FOUND);
        }
        return null;
    }
    @PostMapping("modify")
    public ResponseEntity<ResponseDto> orderModifyTodolist(@RequestBody TodoListUpdateDto todoListUpdateDto){
        ResponseDto responseDto;
        String result = todolistService.modifyTodolist(todoListUpdateDto);
        if(result.equals("success")){
            responseDto = new ResponseDto("success", ".", null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        } else if(result.equals("fail")){
            responseDto = new ResponseDto("fail", ".", null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.NOT_FOUND);
        }
        return null;
    }
    @PostMapping("complete")
    public ResponseEntity<ResponseDto> orderCompleteTodolist(@RequestBody TodolistDto todolistDto){
        ResponseDto responseDto;
        String result = todolistService.completeTodolist(todolistDto.getIdx());
        if(result.equals("success")){
            responseDto = new ResponseDto("success",".",null);
            return new ResponseEntity<ResponseDto>(responseDto,HttpStatus.OK);
        }
        else if(result.equals("fail")){
            responseDto = new ResponseDto("fail", ".", null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.NOT_FOUND);
        }
        return null;
    }
    @PostMapping("select")
    public ResponseEntity<?> orderSelectTodolist(@RequestBody UserInfoDto userInfoDto) {
        Map<String, List<Map<String, Object>>> tasks = todolistService.selectTodolist(userInfoDto.getEmail());
        if (tasks.isEmpty()) {
            return ResponseEntity.ok(new ResponseDto("success", "empty", new ArrayList<>()));
        }
        return ResponseEntity.ok(new ResponseDto("success", ".", tasks));
    }
}
