package com.planisjustnow.data.repository;

import com.planisjustnow.data.entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<AuthEntity,String> {
    AuthEntity findByEmail(String email);
}
