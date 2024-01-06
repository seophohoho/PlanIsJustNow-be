package com.planisjustnow.mvc.service;

import com.planisjustnow.mvc.entity.AuthDto;
import com.planisjustnow.mvc.entity.AuthEntity;
import com.planisjustnow.mvc.repository.AuthRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
@Service
public class AuthService {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private AuthRepository authRepository;
    private String authStr;
    public String createAuthEmailCode(){
        SecureRandom random = new SecureRandom();
        int randomSixDigit = random.nextInt(900000) + 100000;
        return String.valueOf(randomSixDigit);
    }
    public MimeMessage createMailMessage(AuthDto dto, String title) {
        String order = dto.getEmail();
        authStr = createAuthEmailCode();

        saveAuth(new AuthEntity(order,authStr));

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(order);
            helper.setSubject(title);
            helper.setText(authStr);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }
    public void sendEmail(AuthDto dto){
        MimeMessage emailForm = createMailMessage(dto,"PlanIsJustNow 인증 코드 입니다.");
        emailSender.send(emailForm);
    }
    @Transactional
    public AuthEntity saveAuth(AuthEntity entity){
        return authRepository.save(entity);
    }

}
