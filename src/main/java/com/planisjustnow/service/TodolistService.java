package com.planisjustnow.service;

import com.planisjustnow.data.dto.TodoListAddDto;
import com.planisjustnow.data.dto.TodoListDeleteDto;
import com.planisjustnow.data.entity.TodolistEntity;
import com.planisjustnow.data.entity.UserEntity;
import com.planisjustnow.data.repository.TodolistRepository;
import com.planisjustnow.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TodolistService {
    @Autowired
    private TodolistRepository todolistRepository;
    @Autowired
    private UserRepository userRepository;
    public String addTodolist(TodoListAddDto todoListAddDto){
        Optional<UserEntity> user = userRepository.findById(todoListAddDto.getUserId()); // UserEntity 조회
        if(user.isPresent()){
            TodolistEntity todolistEntity = new TodolistEntity(user.get(), todoListAddDto.getTitle(), todoListAddDto.getStartDate(), todoListAddDto.getTime(), todoListAddDto.getIsImportant(), 0);
            todolistRepository.save(todolistEntity);
            return "success";
        }
        return "fail";
    }
    public String deleteTodolist(TodoListDeleteDto todoListDeleteDto){
        try {
            userRepository.deleteById(todoListDeleteDto.getUserId());
            return "success";
        }
        catch (NullPointerException e){

        }
        return "";
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
