package com.planisjustnow.data.repository;

import com.planisjustnow.data.entity.TodolistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TodolistRepository extends JpaRepository <TodolistEntity,Integer> {
    List<TodolistEntity> findAllByUserIdEmail(String userId);
}