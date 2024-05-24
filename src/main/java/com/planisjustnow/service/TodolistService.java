package com.planisjustnow.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.planisjustnow.data.dto.TodoListAddDto;
import com.planisjustnow.data.dto.TodoListUpdateDto;
import com.planisjustnow.data.entity.NatureEntity;
import com.planisjustnow.data.entity.TodolistEntity;
import com.planisjustnow.data.entity.UserEntity;
import com.planisjustnow.data.entity.UserPetEntity;
import com.planisjustnow.data.repository.NatureRepository;
import com.planisjustnow.data.repository.TodolistRepository;
import com.planisjustnow.data.repository.UserPetRepository;
import com.planisjustnow.data.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class TodolistService {
    private final TodolistRepository todolistRepository;
    private final UserRepository userRepository;
    private final NatureRepository natureRepository;
    private final UserPetRepository userPetRepository;

    static final int importantTodoValue = 10;
    static final int normalTodoValue = 5;

    public TodolistService(TodolistRepository todolistRepository, UserRepository userRepository, NatureRepository natureRepository, UserPetRepository userPetRepository) {
        this.todolistRepository = todolistRepository;
        this.userRepository = userRepository;
        this.natureRepository = natureRepository;
        this.userPetRepository = userPetRepository;
    }

    @Transactional
    public Map<String,Object> addTodolist(String userId,TodoListAddDto todoListAddDto){
        Map<String,Object> resultMap = new HashMap<>();
        try{
            Optional<UserEntity> user = userRepository.findById(userId);
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

                UserPetEntity targetUserPet = userPetRepository.findLastChoicePet(user.get());

                if(targetUserPet != null){
                    int result=0;
                    int currentFriendShip = targetUserPet.getCurrentFriendship();
                    NatureEntity targetNature = targetUserPet.getNatureId();
                    int targetNatureBonusFriendship = targetNature.getBonusIncrease();
                    if(todolistEntity.getIsImportant() == 1){
                        result = targetNatureBonusFriendship + importantTodoValue;
                    }else {
                        result = targetNatureBonusFriendship + normalTodoValue;
                    }
                    currentFriendShip = (currentFriendShip) + result;

                    if(currentFriendShip <= targetUserPet.getMaxFriendship_0()){targetUserPet.setEvol(0);}
                    else if(currentFriendShip <= targetUserPet.getMaxFriendship_1()){targetUserPet.setEvol(1);}
                    else if(currentFriendShip <= targetUserPet.getMaxFriendship_2()){targetUserPet.setEvol(2);}

                    targetUserPet.setCurrentFriendship(currentFriendShip);
                    int evol = targetUserPet.getEvol();
                    userPetRepository.save(targetUserPet);
                    return "success"+" "+currentFriendShip+" "+evol;
                }
            }
        }catch (Exception e){
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
    @Scheduled(cron = "0 19 16 * * ?") //<- 00시 00분 00초 *(아무날짜)월 *(아무날짜)일에 해당 메소드를 실행.
    public void calcDropFriendShip(){
        LocalDate today = LocalDate.now();
        LocalDate targetDay = today.minusDays(1); //<-실제 서비스 환경에서는 이 변수 쓰자.
        List<UserEntity> userList = userRepository.findAll();
        for(UserEntity user : userList){
            Long normalTasksCount = todolistRepository.countNormalTask(user, String.valueOf(targetDay));
            Long importantTasksCount = todolistRepository.countImportantTask(user, String.valueOf(targetDay));
            UserPetEntity targetUserPet = userPetRepository.findLastChoicePet(user);
            List<UserPetEntity> userPets = userPetRepository.findAllByUserIdEmail(user.getEmail());
            for(UserPetEntity userPet : userPets){
                if(userPet.getLastChoice() == 1 && userPet != null){
                    int currentFriendShip = userPet.getCurrentFriendship();
                    NatureEntity targetNature = userPet.getNatureId();
                    int targetNatureBonusFriendship = targetNature.getBonusDrop();
                    int userTodoFailureCount = userPet.getUserId().getTodolistFailureCount();
                    int importResult = targetNatureBonusFriendship + importantTodoValue;
                    int normalResult = targetNatureBonusFriendship + normalTodoValue;
                    int todoFailureValue = userTodoFailureCount*2;
                    currentFriendShip = (currentFriendShip) - (importResult + normalResult + todoFailureValue);

                    userPet.setCurrentFriendship(currentFriendShip);
                }
                userPet.setFeed_1(0);
                userPet.setFeed_2(0);
                userPet.setFeed_3(0);
                userPet.setHands(0);
                userPetRepository.save(userPet);
            }
        }
    }
}