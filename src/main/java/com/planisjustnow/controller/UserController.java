package com.planisjustnow.controller;

import com.planisjustnow.data.dto.AccountSignUpDto;
import com.planisjustnow.data.dto.ChoicePetDto;
import com.planisjustnow.data.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;

@Controller
@RequestMapping("/api/user")
public class UserController {
    @PostMapping("choice-pet")
    public ResponseEntity<ResponseDto> orderChoicePet(@RequestBody ChoicePetDto choicePetDto){
        ResponseDto responseDto;
        responseDto = new ResponseDto("success",".");
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
}
