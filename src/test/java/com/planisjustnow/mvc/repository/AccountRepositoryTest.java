package com.planisjustnow.mvc.repository;

import com.planisjustnow.data.entity.UserEntity;
import com.planisjustnow.data.repository.AccountRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
@DataJpaTest
public class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;
    @Test
    void test(){
        UserEntity test = UserEntity.builder()
                .email("test@gmail.com")
                .password("testman")
                .nickname("seophohoho")
                .todolistFailureCount(0)
                .build();
        UserEntity savedUserEntity = accountRepository.save(test);
        UserEntity info = accountRepository.findByEmail(savedUserEntity.getEmail());
        Assertions.assertEquals("test@gmail.com",info.getEmail());
    }
}
