package com.planisjustnow.mvc.repository;

import com.planisjustnow.data.entity.AuthEntity;
import com.planisjustnow.data.repository.AuthRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthRepositoryTest {
    AuthRepository authRepository;
    private AuthEntity entity;
    @After("")
    public void cleanup(){
        authRepository.deleteAll();
    }
    @Test
    public void insertTest(){
        authRepository.save(entity);
    }
}
