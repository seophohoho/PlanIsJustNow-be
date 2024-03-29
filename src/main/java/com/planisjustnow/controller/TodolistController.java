package com.planisjustnow.controller;

import com.planisjustnow.data.dto.ResponseDto;
import com.planisjustnow.data.dto.TodoListAddDto;
import com.planisjustnow.data.dto.UserInfoDto;
import com.planisjustnow.data.entity.UserEntity;
import com.planisjustnow.service.TodolistService;
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
    @PostMapping("add")
    public ResponseEntity<ResponseDto> orderAddTodolist(@RequestBody TodoListAddDto todoListAddDto){
        ResponseDto responseDto;
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
    @DeleteMapping("/delete/{userId}/{title}/{startDate}/{time}")
    public ResponseEntity<ResponseDto> orderDeleteTodolist(@PathVariable String userId, @PathVariable String title, @PathVariable String startDate, @PathVariable String time) {
        ResponseDto responseDto;
        String result = todolistService.deleteTodolist(userId,title,startDate,time);

        if (result.equals("success")) {
            responseDto = new ResponseDto("success", ".", null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        } else if (result.equals("fail")) {
            responseDto = new ResponseDto("fail", ".", null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.NOT_FOUND);
        }
        return null;
    }
    @PostMapping("/select")
    public ResponseEntity<?> selectTodolist(@RequestBody UserInfoDto userInfoDto) {
        Map<String, List<Map<String, Object>>> tasks = todolistService.selectTodolist(userInfoDto.getEmail());
        if (tasks.isEmpty()) {
            return ResponseEntity.ok(new ResponseDto("success", "empty", new ArrayList<>()));
        }
        return ResponseEntity.ok(new ResponseDto("success", ".", tasks));
    }

}
