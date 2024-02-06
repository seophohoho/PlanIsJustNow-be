package com.planisjustnow.service;

import com.planisjustnow.data.dto.AccountSignInDto;
import com.planisjustnow.data.dto.AccountSignUpDto;
import com.planisjustnow.data.entity.UserEntity;
import com.planisjustnow.data.repository.AccountRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String signUp(AccountSignUpDto accountSignUpDto){
        UserEntity accountInfo = new UserEntity(accountSignUpDto.getEmail(),
                passwordEncoder.encode(accountSignUpDto.getPassword()),
                accountSignUpDto.getNickname(),
                0);
        if(accountRepository.existsById(accountSignUpDto.getEmail())){
            return "fail:Email is already in use";
        }

        try{
            saveAccountInfo(accountInfo);
        } catch(Exception e){
            return "fail:Unexpected error";
        }
        return "success";
    }
    public String signIn(AccountSignInDto accountSignInDto){
        try{
            UserEntity accountInfo = findAccountInfo(accountSignInDto.getEmail());
            if(passwordEncoder.matches(accountSignInDto.getPassword(),accountInfo.getPassword())){
                return "success";
            }
            else{
                return "false";
            }
        } catch(NullPointerException e){
            return "false";
        }
    }
    @Transactional
    public UserEntity findAccountInfo(String email){return accountRepository.findByEmail(email);}
    @Transactional
    public UserEntity saveAccountInfo(UserEntity userEntity){
        return accountRepository.save(userEntity);
    }
}
