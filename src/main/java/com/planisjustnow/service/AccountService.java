package com.planisjustnow.service;

import com.planisjustnow.data.dto.AccountSignUpDto;
import com.planisjustnow.data.entity.UserEntity;
import com.planisjustnow.data.repository.AccountRepository;
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
    @Transactional
    public UserEntity saveAccountInfo(UserEntity userEntity){
        return accountRepository.save(userEntity);
    }
}
