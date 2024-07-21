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
import java.security.SecureRandom;
import java.util.Random;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.MessagingException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PasswordResetService {
    // 맵을 통해 비밀번호를 초기화하려는 사용자의 정보 저장
    private final Map<String, String> userDatabase;

    // Password Reset Service
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%&*()_+-=[]|,./?><";

    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + SPECIAL_CHARS;
    private static SecureRandom random = new SecureRandom();
    private static int length = 12;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private AuthService authService; // AuthService 주입, 이메일 인증

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthRepository authRepository;
    public PasswordResetService() {
        // 비밀번호를 초기화하려는 사용자의 정보 맵에 저장
        userDatabase = new HashMap<>(); // 사용자 정보 입력
    }

    // 사용자 입력을 받아 userDatabase에 저장하는 메서드
    public void addUser(String userId, String email) {
        userDatabase.put(userId, email);
    }

    // 비밀번호 초기화 요청을 처리하는 메서드
    public boolean sendMain(String userId, String email) {
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

    public MimeMessage createMailMessage(String email, String password) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(email);
            helper.setSubject("새로운 패스워드입니다.");
            helper.setText(password);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }

    public boolean sendEmail(String email, String password) {
        MimeMessage emailForm = createMailMessage(email, password);
        try {
            emailSender.send(emailForm);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String generatePassword() {
        if (length < 1) {
            throw new IllegalArgumentException("Password length must be greater than 0.");
        }

        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(PASSWORD_ALLOW_BASE.length());
            password.append(PASSWORD_ALLOW_BASE.charAt(randomIndex));
        }
        return password.toString();
    }

    public boolean verificationCode(String email, String code) {
        String vaildCode = authRepository.findByEmail(email).getCode();
        if(vaildCode.equals(code)) {
            UserEntity userEntity = userRepository.findByEmail(email);
            userEntity.setPassword(generatePassword());
            if(!sendEmail(email, userEntity.getPassword())) {
                return false;
            }
            userRepository.save(userEntity);
            return true;
        }
        else {
            return false;
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

    // 비밀번호 초기화 메서드
    public boolean resetPassword(String userId, String nowPassword, String newPassword) {
        // String userEmail = userRepository.findByUsername(userId).getEmail();
        UserEntity userEntity = userRepository.findByUsername(userId);
        if(!userEntity.getPassword().equals(nowPassword)) {
            return false;
        }

        userEntity.setPassword(newPassword);
        userRepository.save(userEntity);
        return true;
    }
}
