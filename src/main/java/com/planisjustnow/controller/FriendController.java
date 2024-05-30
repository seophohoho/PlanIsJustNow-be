package com.planisjustnow.controller;

import com.planisjustnow.data.dto.ResponseDto;
import com.planisjustnow.data.dto.UserInfoDto;
import com.planisjustnow.service.FriendService;
import com.planisjustnow.service.TodolistService;
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
@RequestMapping("/api/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private TodolistService todolistService;
    @Autowired
    private UserPetService userPetService;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("request")
    public ResponseEntity<ResponseDto> orderRequestFriend(@RequestBody UserInfoDto userInfoDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String userId = jwtUtil.parseToken(httpServletRequest, httpServletResponse);
        if (userId.equals("fail:Token-not-found")) {
            ResponseDto responseDto = new ResponseDto("redirect", "/", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED);
        }
        String result = friendService.requestFriend(userId, userInfoDto);
        if (result.equals("success")) {
            ResponseDto responseDto = new ResponseDto("success", ".", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }else if(result.equals("fail:Self")){
            ResponseDto responseDto = new ResponseDto("fail", "Self Request error", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }else if(result.equals("fail:Exist-request")){
            ResponseDto responseDto = new ResponseDto("fail", "Exist Request error", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }else if(result.equals("fail:Exist-friend")) {
            ResponseDto responseDto = new ResponseDto("fail", "Exist Friend error", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }else if(result.equals("fail:Exist-target")) {
            ResponseDto responseDto = new ResponseDto("fail", "Exist Target Request error", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }else if(result.equals("fail:nothing")){
            ResponseDto responseDto = new ResponseDto("fail", "Not Exist User", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }else {
            ResponseDto responseDto = new ResponseDto("fail", "Unexpected error", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("request-count")
    public ResponseEntity<ResponseDto> orderRequestCount(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        ResponseDto responseDto;
        String userId = jwtUtil.parseToken(httpServletRequest, httpServletResponse);
        if (userId.equals("fail:Token-not-found")) {
            responseDto = new ResponseDto("redirect", "/", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED);
        }
        List<Object> result = friendService.selectFriend(userId,"request");
        if(result.isEmpty()){
            responseDto = new ResponseDto("success","empty",0,null);
        }
        else{
            responseDto = new ResponseDto("success", ".", result.size(),null);
        }
        return new ResponseEntity<ResponseDto>(responseDto,HttpStatus.OK);
    }
    @GetMapping("request-select")
    public ResponseEntity<ResponseDto> orderSelectRequestFriend(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        ResponseDto responseDto;
        String userId = jwtUtil.parseToken(httpServletRequest, httpServletResponse);
        if (userId.equals("fail:Token-not-found")) {
            responseDto = new ResponseDto("redirect", "/", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED);
        }
        List<Object> result = friendService.selectFriend(userId,"request");
        if(result.isEmpty()){
            responseDto = new ResponseDto("success","empty",new ArrayList<>(),null);
        }
        else{
            responseDto = new ResponseDto("success", ".", result,null);
        }
        return new ResponseEntity<ResponseDto>(responseDto,HttpStatus.OK);
    }
    @PostMapping("request-accept")
    public ResponseEntity<ResponseDto> orderRequestFriendAccept(@RequestBody UserInfoDto userInfoDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        ResponseDto responseDto;
        String userId = jwtUtil.parseToken(httpServletRequest, httpServletResponse);
        if (userId.equals("fail:Token-not-found")) {
            responseDto = new ResponseDto("redirect", "/", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED);
        }
        String result = friendService.requestFriendAccept(userId,userInfoDto);
        if (result.equals("success")) {
            responseDto = new ResponseDto("success", ".", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        } else {
            responseDto = new ResponseDto("fail", "Unexpected error", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("request-reject")
    public ResponseEntity<ResponseDto> orderRequestFriendReject(@RequestBody UserInfoDto userInfoDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        ResponseDto responseDto;
        String userId = jwtUtil.parseToken(httpServletRequest, httpServletResponse);
        if (userId.equals("fail:Token-not-found")) {
            responseDto = new ResponseDto("redirect", "/", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED);
        }
        String result = friendService.requestFriendReject(userId, userInfoDto);
        if (result.equals("success")) {
            responseDto = new ResponseDto("success", ".", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        } else {
            responseDto = new ResponseDto("fail", "Unexpected error", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("select")
    public ResponseEntity<ResponseDto> orderSelectFriend(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest){
        ResponseDto responseDto;
        String userId = jwtUtil.parseToken(httpServletRequest, httpServletResponse);
        if (userId.equals("fail:Token-not-found")) {
            responseDto = new ResponseDto("redirect", "/", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED);
        }
        List<Object> result = friendService.selectFriend(userId,"real");
        if(result.isEmpty()){
            responseDto = new ResponseDto("success","empty",new ArrayList<>(),null);
        }
        else{
            responseDto = new ResponseDto("success", ".", result,null);
        }
        return new ResponseEntity<ResponseDto>(responseDto,HttpStatus.OK);
    }
    @PostMapping("select-detail-todolist")
    public ResponseEntity<ResponseDto> orderSelectTodolistDetail(@RequestBody UserInfoDto userInfoDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        ResponseDto responseDto;
        String userId = jwtUtil.parseToken(httpServletRequest, httpServletResponse);
        if (userId.equals("fail:Token-not-found")) {
            responseDto = new ResponseDto("redirect", "/", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED);
        }
        Map<String, List<Map<String, Object>>> tasks = todolistService.selectTodolist(userInfoDto.getEmail());
        if (tasks.isEmpty()) {
            responseDto = new ResponseDto("success","empty",new ArrayList<>(),null);
            return new ResponseEntity<ResponseDto>(responseDto,HttpStatus.OK);
        }
        else if(tasks == null){
            responseDto = new ResponseDto("fail", "Unexpected error", null,null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.NOT_FOUND);
        }
        else{
            responseDto = new ResponseDto("success", ".", tasks,null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        }
    }
    @PostMapping("select-detail-pet")
    public ResponseEntity<ResponseDto> orderSelectPetDetail(@RequestBody UserInfoDto userInfoDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        ResponseDto responseDto;
        String userId = jwtUtil.parseToken(httpServletRequest, httpServletResponse);
        if (userId.equals("fail:Token-not-found")) {
            responseDto = new ResponseDto("redirect", "/", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED);
        }
        Map<String, Object> result = userPetService.isHasPet(userInfoDto.getEmail());
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
    @PostMapping("delete")
    public ResponseEntity<ResponseDto> orderDeleteFriend(@RequestBody UserInfoDto userInfoDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        ResponseDto responseDto;
        String userId = jwtUtil.parseToken(httpServletRequest, httpServletResponse);
        if (userId.equals("fail:Token-not-found")) {
            responseDto = new ResponseDto("redirect", "/", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.UNAUTHORIZED);
        }
        String result = friendService.deleteFriend(userId,userInfoDto);
        if (result.equals("success")) {
            responseDto = new ResponseDto("success", ".", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        } else {
            responseDto = new ResponseDto("fail", "Unexpected error", null, null);
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}