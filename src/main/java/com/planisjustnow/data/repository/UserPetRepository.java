package com.planisjustnow.data.repository;

import com.planisjustnow.data.entity.UserPetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPetRepository extends JpaRepository<UserPetEntity, Integer> {
    List<UserPetEntity> findAllByUserIdEmail(String userId);
}