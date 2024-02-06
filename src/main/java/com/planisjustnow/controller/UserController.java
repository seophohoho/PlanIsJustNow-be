package com.planisjustnow.controller;

import com.planisjustnow.data.dto.AccountSignUpDto;
import com.planisjustnow.data.dto.ChoicePetDto;
import com.planisjustnow.data.dto.ResponseDto;
import com.planisjustnow.data.dto.UserInfoDto;
import com.planisjustnow.service.UserPetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;

@Controller
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserPetService userPetService;
    @PostMapping("choice-pet")
    public ResponseEntity<ResponseDto> orderChoicePet(@RequestBody ChoicePetDto choicePetDto){
        String result = userPetService.setUserPet(choicePetDto);
        if(result.equals("success")){
            ResponseDto responseDto = new ResponseDto("success",".");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
        else{
            ResponseDto responseDto = new ResponseDto("fail","Unexpected error");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("has-pet")
    public ResponseEntity<ResponseDto> orderIsHasPet(@RequestBody UserInfoDto userInfoDto){
        String result = userPetService.isHasPet(userInfoDto);
        if(result.equals("success:has")){
            ResponseDto responseDto = new ResponseDto("success","has");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
        else if(result.equals("success:nothing")){
            ResponseDto responseDto = new ResponseDto("success","nothing");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
        else{
            ResponseDto responseDto = new ResponseDto("fail","Unexpected error");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
