package com.planisjustnow.controller;

import com.planisjustnow.data.dto.ResponseDto;
import com.planisjustnow.data.dto.UserInfoDto;
import com.planisjustnow.service.TodolistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/api/todolist")
public class TodoListController {
    @Autowired
    private TodolistService todolistService;
    @PostMapping("select")
    public ResponseEntity<ResponseDto> orderSelectTodolist(@RequestBody UserInfoDto userInfoDto){
        Map<String,Object> result = todolistService.selectTodolist(userInfoDto.getEmail());
        ResponseDto responseDto;
        if(result.get("result").equals("success")){
            responseDto = new ResponseDto("success",".",result.get("lst"));
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
        if(result.get("result").equals("success-empty")){
            responseDto = new ResponseDto("success","empty",result.get("lst"));
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
        if(result.equals("fail:Null_point_exception")){
            responseDto = new ResponseDto("fail","Null point error",null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
        if(result.equals("fail:Empty_result_data_access_exception")){
            responseDto = new ResponseDto("fail","Empty result data access error",null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

}
