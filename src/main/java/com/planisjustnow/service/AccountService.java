package com.planisjustnow.service;

import com.planisjustnow.data.dto.AccountSignInDto;
import com.planisjustnow.data.dto.AccountSignUpDto;
import com.planisjustnow.data.entity.UserEntity;
import com.planisjustnow.data.repository.AccountRepository;
import com.planisjustnow.utils.JwtUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Duration;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JwtUtil jwtUtil;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String signUp(String email, String password, String nickname, String profileUrl){
        UserEntity accountInfo = new UserEntity(email,
                passwordEncoder.encode(password),
                nickname,
                0,
                profileUrl);
        if(accountRepository.existsById(email)){
            return "fail:Email is already in use";
        }

        try{
            saveAccountInfo(accountInfo);
        } catch(Exception e){
            return "fail:Unexpected error";
        }
        return "success";
    }
    public String signIn(AccountSignInDto accountSignInDto,HttpServletResponse response){
        try{
            UserEntity accountInfo = findAccountInfo(accountSignInDto.getEmail());
            if(passwordEncoder.matches(accountSignInDto.getPassword(),accountInfo.getPassword())){
                return "success";
            }
            else{
                return "false:Not matche";
            }
        } catch(Exception e){
            System.err.println(e);
            return "false:Not matche";
        }
    }
    @Transactional
    public UserEntity findAccountInfo(String email){return accountRepository.findByEmail(email);}
    @Transactional
    public UserEntity saveAccountInfo(UserEntity userEntity){
        return accountRepository.save(userEntity);
    }
}
