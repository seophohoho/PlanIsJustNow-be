package com.planisjustnow.data.repository;

import com.planisjustnow.data.entity.TodolistEntity;
import com.planisjustnow.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
public interface TodolistRepository extends JpaRepository <TodolistEntity,Long> {
    List<TodolistEntity> findAllByUserIdEmail(String userId);
    @Transactional
    @Modifying
    @Query("DELETE FROM TodolistEntity t WHERE t.userId = :user AND t.idx = :idx")
    void deleteByUserIdAndIdx(@Param("user") UserEntity user, @Param("idx") Long idx);
}