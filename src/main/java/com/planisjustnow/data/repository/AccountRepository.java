package com.planisjustnow.data.repository;

import com.planisjustnow.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository <UserEntity,String> {
}
