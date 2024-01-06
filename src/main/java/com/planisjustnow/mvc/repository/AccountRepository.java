package com.planisjustnow.mvc.repository;

import com.planisjustnow.mvc.entity.AccountSignUpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository <AccountSignUpEntity,String> {

}
