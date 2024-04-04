package com.planisjustnow.service;

import com.planisjustnow.data.dto.TodoListAddDto;
import com.planisjustnow.data.dto.TodoListUpdateDto;
import com.planisjustnow.data.dto.TodolistDto;
import com.planisjustnow.data.entity.TodolistEntity;
import com.planisjustnow.data.entity.UserEntity;
import com.planisjustnow.data.repository.TodolistRepository;
import com.planisjustnow.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;

@Service
public class TodolistService {
    private final TodolistRepository todolistRepository;
    private final UserRepository userRepository;

    public TodolistService(TodolistRepository todolistRepository, UserRepository userRepository) {
        this.todolistRepository = todolistRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String addTodolist(TodoListAddDto todoListAddDto){
        Optional<UserEntity> user = userRepository.findById(todoListAddDto.getUserId()); // UserEntity 조회
        if(user.isPresent()){
            TodolistEntity todolistEntity = new TodolistEntity(user.get(), todoListAddDto.getTitle(), todoListAddDto.getStartDate(), todoListAddDto.getTime(), todoListAddDto.getIsImportant(), 0);
            todolistRepository.save(todolistEntity);
            return "success";
        }
        return "fail";
    }
    public String deleteTodolist(Long idx){
        try {
            todolistRepository.deleteById(idx);
            return "success";
        }
        catch (NullPointerException e){
            return "fail";
        }
    }
    public String modifyTodolist(TodoListUpdateDto todoListUpdateDto){
        Optional<TodolistEntity> todolistEntityOptional = todolistRepository.findById(todoListUpdateDto.getIdx());

        if(todolistEntityOptional.isPresent()){
            TodolistEntity todolistEntity = todolistEntityOptional.get();

            todolistEntity.setTitle(todoListUpdateDto.getTitle());
            todolistEntity.setStartDate(todoListUpdateDto.getStartDate());
            todolistEntity.setTime(todoListUpdateDto.getTime());
            todolistEntity.setIsImportant(todoListUpdateDto.getIsImportant());

            todolistRepository.save(todolistEntity);
            return "success";
        }
        else{
            return "fail";
        }
    }
    public String completeTodolist(Long idx){
        Optional<TodolistEntity> todolistEntityOptional = todolistRepository.findById(idx);
        if(todolistEntityOptional.isPresent()){
            TodolistEntity todolistEntity = todolistEntityOptional.get();

            todolistEntity.setIsComplete(1);
            todolistRepository.save(todolistEntity);
            return "success";
        }
        else{
            return "fail";
        }
    }
    public Map<String, List<Map<String, Object>>> selectTodolist(String userId) {
        Map<String, List<Map<String, Object>>> groupedTasks = new HashMap<>();
        List<TodolistEntity> tasks = todolistRepository.findAllByUserIdEmail(userId);

        for (TodolistEntity task : tasks) {
            String startDate = task.getStartDate();
            Map<String, Object> taskDetails = new HashMap<>();
            taskDetails.put("title", task.getTitle());
            taskDetails.put("time", task.getTime());
            taskDetails.put("isImportant", task.getIsImportant());
            taskDetails.put("isComplete", task.getIsComplete());

            groupedTasks.computeIfAbsent(startDate, k -> new ArrayList<>()).add(taskDetails);
        }

        return groupedTasks;
    }
}
