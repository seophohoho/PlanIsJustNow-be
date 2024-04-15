package com.planisjustnow.controller;

import com.planisjustnow.data.dto.FriendDto;
import com.planisjustnow.data.dto.ResponseDto;
import com.planisjustnow.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    // 친구 요청
    @PostMapping("friendRequest")
    public ResponseEntity<ResponseDto> sendFriendRequest(@RequestBody FriendDto friendDto) {
        String result = friendService.friendRequest(friendDto);
        if (result.equals("success")) {
            ResponseDto responseDto = new ResponseDto("success", ".");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        } else if (result.equals("fail")) {
            ResponseDto responseDto = new ResponseDto("fail", "friendRequest fail");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        } else {
            ResponseDto responseDto = new ResponseDto("fail", "Unexpected error");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 친구 수락
    @PostMapping("requestAccept")
    public ResponseEntity<ResponseDto> acceptFriendRequest(@RequestBody FriendDto friendDto) {
        String result = friendService.requestAccept(friendDto);
        if (result.equals("success")) {
            ResponseDto responseDto = new ResponseDto("success", ".");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        } else {
            ResponseDto responseDto = new ResponseDto("fail", "friendRequest Accept fail");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    // 친구 거절
    @PostMapping("requestRefuse")
    public ResponseEntity<ResponseDto> refuseFriendRequest(@RequestBody FriendDto friendDto) {
        String result = friendService.requestRefuse(friendDto);
        if (result.equals("success")) {
            ResponseDto responseDto = new ResponseDto("success", ".");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
        } else {
            ResponseDto responseDto = new ResponseDto("fail", "friendRequest refuse fail");
            return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    // 친구 조회
    //@PostMapping("friend-inquiry");

    // 친구 삭제
    //@PostMapping("friend-delete");

}
