package com.planisjustnow.service;

import com.planisjustnow.data.dto.UserInfoDto;
import com.planisjustnow.data.entity.FriendEntity;
import com.planisjustnow.data.entity.TodolistEntity;
import com.planisjustnow.data.entity.UserEntity;
import com.planisjustnow.data.repository.FriendRepository;
import com.planisjustnow.data.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class FriendService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    public FriendService(UserRepository userRepository, FriendRepository friendRepository){
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
    }
    public String requestFriend(String userId, UserInfoDto userInfoDto){
        try {
            LocalDate today = LocalDate.now();
            Optional<UserEntity> user = userRepository.findById(userId);
            Optional<UserEntity> targetUser = userRepository.findById(userInfoDto.getEmail());
            if(user.get().getEmail().equals(targetUser.get().getEmail())){
                return "fail:Self";
            }
            if(friendRepository.isExistRequest(user.get(),targetUser.get())){
                return "fail:Exist-request";
            }
            if(friendRepository.isExist(user.get(),targetUser.get())){
                return "fail:Exist-friend";
            }
            if(friendRepository.isExistTargetRequest(user.get(),targetUser.get())){
                return "fail:Exist-target";
            }
            FriendEntity friendEntity = new FriendEntity(targetUser.get(),user.get(),0,String.valueOf(today));
            friendRepository.save(friendEntity);
        }catch (Exception e){
            return "fail";
        }
        return "success";
    }
    public List<Object> selectFriend(String userId,String type){
        Map<String, Map<String, Object>> groupedTasks = new HashMap<>();
        try{
            Optional<UserEntity> user = userRepository.findById(userId);
            List<FriendEntity> friends = null;
            List<Object> lst = new ArrayList<>();
            if(type.equals("request")){
                friends = friendRepository.findAllRequestFriend(user.get());
            }
            else if(type.equals("real")){
                friends = friendRepository.findAllRealFriend(user.get());
            }

            for(FriendEntity friend : friends){
                Map<String,Object> friendDetails = new HashMap<>();
                friendDetails.put("email",friend.getToUser().getEmail());
                friendDetails.put("nickname",friend.getToUser().getNickname());
                friendDetails.put("profile",friend.getToUser().getImageUrl());
                friendDetails.put("startDate",friend.getStartDate());
                lst.add(friendDetails);
            }
            return lst;
        }catch (Exception e){
            return null;
        }
    }
    public String requestFriendAccept(String userId,UserInfoDto userInfoDto){
        try{
            LocalDate today = LocalDate.now();

            Optional<UserEntity> user = userRepository.findById(userId);
            Optional<UserEntity> targetUser = userRepository.findById(userInfoDto.getEmail());
            Optional<FriendEntity> friend = friendRepository.findByFriendRequestInfo(user.get(),targetUser.get());
            friend.get().setIsFriend(1);
            FriendEntity friendEntity = new FriendEntity(targetUser.get(),user.get(),1,String.valueOf(today));

            if(user.get().getEmail().equals(targetUser.get().getEmail())){
                return "fail";
            }
            friendRepository.save(friend.get());
            friendRepository.save(friendEntity);

        }catch (Exception e){
            return "fail";
        }
        return "success";
    }
    public String requestFriendReject(String userId, UserInfoDto userInfoDto){
        try {
            Optional<UserEntity> user = userRepository.findById(userId);
            Optional<UserEntity> targetUser = userRepository.findById(userInfoDto.getEmail());
            friendRepository.deleteFriendRequest(user.get(),targetUser.get());
        }catch (Exception e){
            return "fail";
        }
        return "success";
    }
    public String deleteFriend(String userId, UserInfoDto userInfoDto){
        try {
            Optional<UserEntity> user = userRepository.findById(userId);
            Optional<UserEntity> targetUser = userRepository.findById(userInfoDto.getEmail());
            System.out.println("check1");
            friendRepository.deleteFriend(user.get(),targetUser.get());
            System.out.println("check2");
            friendRepository.deleteFriend(targetUser.get(),user.get());
            System.out.println("check3");
        }catch (Exception e){
            return "fail";
        }
        return "success";
    }
}