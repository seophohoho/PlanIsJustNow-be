package com.planisjustnow.service;

import com.planisjustnow.data.dto.FriendDto;
import com.planisjustnow.data.entity.FriendEntity;
import com.planisjustnow.data.entity.UserEntity;
import com.planisjustnow.data.repository.FriendRepository;
import com.planisjustnow.data.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private UserRepository userRepository;
    @Transactional
    public String friendRequest(FriendDto friendDto) {

        // 클라이언트로부터 받은 FriendDto에서 요청 정보 추가하기

        // 요청 정보를 기반으로 새로운 친구 요청 Entity 생성하기

        try{
            String fromUser = friendDto.getFrom();
            String toUser = friendDto.getTo();
            Optional<UserEntity> fromUserEntity = userRepository.findById(fromUser);
            Optional<UserEntity> toUserEntity = userRepository.findById(toUser);

            FriendEntity friendRequest1 = new FriendEntity();
            friendRequest1.setFrom(fromUserEntity.get());
            friendRequest1.setTo(toUserEntity.get());
            friendRequest1.setIs_friend(0);
            friendRepository.save(friendRequest1);

            FriendEntity friendRequest2 = new FriendEntity();
            friendRequest2.setFrom(toUserEntity.get());
            friendRequest2.setTo(fromUserEntity.get());
            friendRequest2.setIs_friend(0);
            friendRepository.save(friendRequest2);
            return "success";
        } catch (NullPointerException e) {
            return "fail";
        }
    }

}
