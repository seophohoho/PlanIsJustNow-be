package com.planisjustnow.data.repository;

import com.planisjustnow.data.entity.UserEntity;
import com.planisjustnow.data.entity.UserPetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPetRepository extends JpaRepository<UserPetEntity, Integer> {
    List<UserPetEntity> findAllByUserIdEmail(String userId);
    @Query("SELECT t FROM UserPetEntity t WHERE t.userId = :user AND t.lastChoice = 1")
    UserPetEntity findLastChoicePet(@Param("user") UserEntity user);
}