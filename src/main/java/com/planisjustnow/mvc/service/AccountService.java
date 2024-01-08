package com.planisjustnow.mvc.service;

import com.planisjustnow.mvc.entity.AccountSignUpDto;
import com.planisjustnow.mvc.entity.AccountSignUpEntity;
import com.planisjustnow.mvc.repository.AccountRepository;
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
        AccountSignUpEntity accountInfo = new AccountSignUpEntity(accountSignUpDto.getEmail(),
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
    public AccountSignUpEntity saveAccountInfo(AccountSignUpEntity accountSignUpEntity){
        return accountRepository.save(accountSignUpEntity);
    }
}
