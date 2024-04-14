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
    public Map<String,Object> addTodolist(String userId,TodoListAddDto todoListAddDto){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            Optional<UserEntity> user = userRepository.findById(userId); // UserEntity 조회
            if(user.isPresent()){
                TodolistEntity todolistEntity = new TodolistEntity(user.get(), todoListAddDto.getTitle(), todoListAddDto.getStartDate(), todoListAddDto.getTime(), todoListAddDto.getIsImportant(), 0);
                TodolistEntity savedEntity = todolistRepository.save(todolistEntity);
                Long savedIdx = savedEntity.getIdx();
                resultMap.put("result","success");
                resultMap.put("data",savedIdx);
            }
        }
        catch(NullPointerException e){
            resultMap.put("result","fail");
            resultMap.put("data",null);
        }
        return resultMap;
    }
    public String deleteTodolist(String userId,Long idx){
        try {
            Optional<UserEntity> user = userRepository.findById(userId);
            todolistRepository.deleteByUserIdAndIdx(user.get(),idx);
            return "success";
        }
        catch (NullPointerException e){
            return "fail";
        }
    }
    public String modifyTodolist(String userId,TodoListUpdateDto todoListUpdateDto){
        try{
            Optional<UserEntity> user = userRepository.findById(userId);
            Optional<TodolistEntity> todolistEntityOptional = todolistRepository.findById(todoListUpdateDto.getIdx());
            if(todolistEntityOptional.isPresent()){
                TodolistEntity todolistEntity = todolistEntityOptional.get();
                todolistEntity.setUserId(user.get());
                todolistEntity.setTitle(todoListUpdateDto.getTitle());
                todolistEntity.setStartDate(todoListUpdateDto.getStartDate());
                todolistEntity.setTime(todoListUpdateDto.getTime());
                todolistEntity.setIsImportant(todoListUpdateDto.getIsImportant());

                todolistRepository.save(todolistEntity);
                return "success";
            }
        }catch (NullPointerException e){
            return "fail";
        }
        return null;
    }
    public String completeTodolist(String userId, Long idx){
        try{
            Optional<UserEntity> user = userRepository.findById(userId);
            Optional<TodolistEntity> todolistEntityOptional = todolistRepository.findById(idx);
            if(todolistEntityOptional.isPresent()){
                TodolistEntity todolistEntity = todolistEntityOptional.get();
                todolistEntity.setUserId(user.get());
                todolistEntity.setIsComplete(1);
                todolistRepository.save(todolistEntity);
                return "success";
            }
        }catch (NullPointerException e){
            return "fail";
        }
        return null;
    }
    public Map<String, List<Map<String, Object>>> selectTodolist(String userId) {
        try{
            Map<String, List<Map<String, Object>>> groupedTasks = new HashMap<>();
            List<TodolistEntity> tasks = todolistRepository.findAllByUserIdEmail(userId);

            for (TodolistEntity task : tasks) {
                String startDate = task.getStartDate();
                Map<String, Object> taskDetails = new HashMap<>();
                taskDetails.put("idx",task.getIdx());
                taskDetails.put("title", task.getTitle());
                taskDetails.put("time", task.getTime());
                taskDetails.put("important", task.getIsImportant());
                taskDetails.put("complete", task.getIsComplete());

                groupedTasks.computeIfAbsent(startDate, k -> new ArrayList<>()).add(taskDetails);
            }
            return groupedTasks;
        }catch (NullPointerException e){
            return null;
        }
    }
}
