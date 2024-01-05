package com.planisjustnow.mvc.controller;

import com.planisjustnow.mvc.entity.AuthDTO;
import com.planisjustnow.mvc.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthService authService;
    private String authEmailCode;
    @PostMapping("/auth/mail")
    public void orderMailConfirm(@RequestBody AuthDTO authdto){
        authService.sendEmail(authdto);
    }
}
