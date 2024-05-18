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
            FriendEntity friendEntity = new FriendEntity(targetUser.get(),user.get(),0,String.valueOf(today));

            friendRepository.save(friendEntity);

        }catch (Exception e){
            return "fail";
        }
        return "success";
    }
    public Map<String, List<Map<String, Object>>> requestSelectFriend(String userId){
        Map<String, List<Map<String, Object>>> groupedTasks = new HashMap<>();
        try{
            Optional<UserEntity> user = userRepository.findById(userId);
            List<FriendEntity> friends = friendRepository.findAllByUserIdEmail(user.get());

            for(FriendEntity friend : friends){
                System.out.println(friend.getToUser().getEmail());
                Map<String,Object> friendDetails = new HashMap<>();
                friendDetails.put("nickname",friend.getToUser().getNickname());
                friendDetails.put("profile",friend.getToUser().getImageUrl());
                friendDetails.put("startDate",friend.getStartDate());

                groupedTasks.computeIfAbsent(friend.getToUser().getEmail(), k -> new ArrayList<>()).add(friendDetails);
            }
            return groupedTasks;
        }catch (Exception e){
            System.out.println(e);
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

            friendRepository.save(friend.get());
            friendRepository.save(friendEntity);

        }catch (Exception e){
            return null;
        }
        return "success";
    }
    public String requestFriendReject(String userId, UserInfoDto userInfoDto){
        try {
            Optional<UserEntity> user = userRepository.findById(userId);
            Optional<UserEntity> targetUser = userRepository.findById(userInfoDto.getEmail());
            friendRepository.deleteFriendRequest(user.get(),targetUser.get());
        }catch (Exception e){
            return null;
        }
        return "success";
    }
}
