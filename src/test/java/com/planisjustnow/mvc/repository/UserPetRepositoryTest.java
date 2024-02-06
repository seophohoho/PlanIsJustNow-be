package com.planisjustnow.mvc.repository;

import com.planisjustnow.data.entity.UserPetEntity;
import com.planisjustnow.data.repository.UserPetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class UserPetRepositoryTest {
    @Autowired
    UserPetRepository userPetRepository;
    @Test
    void test(){
        List<UserPetEntity> list = userPetRepository.findAllByUserId("test@gmail.com");
    }

}
