package com.planisjustnow.service;

import com.planisjustnow.data.dto.TodoListDto;
import com.planisjustnow.data.entity.TodolistEntity;
import com.planisjustnow.data.entity.UserEntity;
import com.planisjustnow.data.repository.TodolistRepository;
import com.planisjustnow.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TodolistService {
    @Autowired
    private TodolistRepository todolistRepository;
    @Autowired
    private UserRepository userRepository;
    public String addTodolist(TodoListDto todoListDto){
        Optional<UserEntity> user = userRepository.findById(todoListDto.getUserId()); // UserEntity 조회
        if(user.isPresent()){
            TodolistEntity todolistEntity = new TodolistEntity(user.get(), todoListDto.getTitle(), todoListDto.getStartDate(),todoListDto.getEndDate(),todoListDto.getTime(), todoListDto.getIsImportant(), 0);
            todolistRepository.save(todolistEntity);
            return "success";
        }
        return "fail";
    }
    public Map<String,Object> selectTodolist(String email){
        Map<String,Object> result = new HashMap<>();
        try{
            List<TodolistEntity> lst = todolistRepository.findAllByUserIdEmail(email);
            if(lst.size() > 0){
                result.put("result","success");
                result.put("lst",lst);
            }
            else if(lst.size() == 0){
                result.put("result","success-empty");
                result.put("lst",lst);
            }
        }catch(NullPointerException e){
            result.put("result","fail:Null_point_exception");
        }catch(EmptyResultDataAccessException e) {
            result.put("result", "fail:Empty_result_data_access_exception");
        }
        return result;
    }
}
