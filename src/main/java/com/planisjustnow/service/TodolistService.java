package com.planisjustnow.service;

import com.planisjustnow.data.entity.TodolistEntity;
import com.planisjustnow.data.repository.TodolistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TodolistService {
    @Autowired
    private TodolistRepository todolistRepository;
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
