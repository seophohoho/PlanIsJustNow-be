package com.planisjustnow.mvc.controller;

import com.planisjustnow.mvc.entity.AccountSignUpDto;
import com.planisjustnow.mvc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PostMapping("account/signup")
    public void orderSignUp(@RequestBody AccountSignUpDto accountSignUpDto){
        accountService.signUp(accountSignUpDto);
    }
}
