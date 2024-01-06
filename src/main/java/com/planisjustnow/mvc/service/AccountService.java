package com.planisjustnow.mvc.service;

import com.planisjustnow.mvc.entity.AccountSignUpDto;
import com.planisjustnow.mvc.entity.AccountSignUpEntity;
import com.planisjustnow.mvc.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    public void signUp(AccountSignUpDto accountSignUpDto){
        AccountSignUpEntity accountInfo = new AccountSignUpEntity(accountSignUpDto.getEmail(),
                accountSignUpDto.getPassword(),
                accountSignUpDto.getNickname(),
                0);
        saveAccountInfo(accountInfo);
    }
    @Transactional
    public AccountSignUpEntity saveAccountInfo(AccountSignUpEntity accountSignUpEntity){
        return accountRepository.save(accountSignUpEntity);
    }
}
