package com.planisjustnow.data.repository;

import com.planisjustnow.data.entity.FriendEntity;
import com.planisjustnow.data.entity.TodolistEntity;
import com.planisjustnow.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<FriendEntity, Long> {
    @Query("SELECT t FROM FriendEntity t WHERE t.fromUser = :user AND t.isFriend = 0")
    List<FriendEntity> findAllByUserIdEmail(@Param("user") UserEntity user);
}