package com.planisjustnow.data.repository;

import com.planisjustnow.data.entity.TodolistEntity;
import com.planisjustnow.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface TodolistRepository extends JpaRepository <TodolistEntity,Integer> {
    List<TodolistEntity> findAllByUserIdEmail(String userId);
    @Transactional
    @Modifying
    @Query("delete from TodolistEntity t WHERE t.userId.id = :userId AND t.title = :title AND t.startDate = :startDate AND t.time = :time")
    void deleteByUserIdAndTitleAndStartDateAndTimeAndIsCompleteFalse(@Param("userId") String userId, @Param("title") String title, @Param("startDate") String startDate, @Param("time") String time);
}