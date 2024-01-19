package com.planisjustnow.data.repository;

import com.planisjustnow.data.entity.AccountSignUpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository <AccountSignUpEntity,String> {
}
