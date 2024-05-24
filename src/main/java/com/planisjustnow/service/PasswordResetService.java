package com.planisjustnow.service;

import com.planisjustnow.data.dto.AuthDto;
import com.planisjustnow.data.dto.ResponseDto;
import com.planisjustnow.data.entity.UserEntity;
import com.planisjustnow.data.repository.AuthRepository;
import com.planisjustnow.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PasswordResetService {
    // 맵을 통해 비밀번호를 초기화하려는 사용자의 정보 저장
    private final Map<String, String> userDatabase;

    @Autowired
    private AuthService authService; // AuthService 주입, 이메일 인증

    @Autowired
    private UserRepository userRepository;
    public PasswordResetService() {
        // 비밀번호를 초기화하려는 사용자의 정보 맵에 저장
        userDatabase = new HashMap<>(); // 사용자 정보 입력
    }

    // 사용자 입력을 받아 userDatabase에 저장하는 메서드
    public void addUser(String userId, String email) {
        userDatabase.put(userId, email);
    }

    // 비밀번호 초기화 요청을 처리하는 메서드
    public boolean resetPassword(String userId, String email) {
        String userEmail = userRepository.findByUsername(userId).getEmail();
        if(userEmail.equals(email)) {
            AuthDto authDto = new AuthDto();
            authDto.setEmail(email);
            sendEmailVerificationCode(authDto);
            return true; //이메일 전송 성공
        }
        else {
            return false; //이메일 전송 실패
        }
    }

    // 이메일 인증 요청 메서드
    public ResponseEntity<ResponseDto> sendEmailVerificationCode(AuthDto authdto) {
        String result = authService.sendEmail(authdto);
        ResponseDto responseDto;
        if (result.equals("success")) {
            responseDto = new ResponseDto("success", ".", null, null);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            responseDto = new ResponseDto("fail", "nothing data.", null, null);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    // 이메일 인증 코드 확인 메서드
    public ResponseEntity<ResponseDto> checkEmailVerificationCode(AuthDto authDto) {
        String result = authService.checkAuthCode(authDto);
        if (result.equals("success")) {
            ResponseDto responseDto = new ResponseDto("success", ".", null, null);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            ResponseDto responseDto = new ResponseDto("fail", "Unexpected error", null, null);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
