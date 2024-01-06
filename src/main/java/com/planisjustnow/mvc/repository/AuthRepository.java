package com.planisjustnow.mvc.repository;

import com.planisjustnow.mvc.entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<AuthEntity,String> {
    AuthEntity findByEmail(String email);
}
