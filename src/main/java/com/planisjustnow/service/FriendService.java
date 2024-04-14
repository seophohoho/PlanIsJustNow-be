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

@Service
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String friendRequest(FriendDto friendDto) {

        // 클라이언트로부터 받은 FriendDto에서 요청 정보 추가하기
        String fromUser = friendDto.getFrom();
        String toUser = friendDto.getTo();

        // 요청 정보를 기반으로 새로운 친구 요청 Entity 생성하기

        try{
            // (from - to - isFriend)
            FriendEntity friendRequest1 = new FriendEntity();
            friendRequest1.setFrom(userRepository.findById(fromUser).orElse(null));
            friendRequest1.setTo(userRepository.findById(toUser).orElse(null));
            friendRequest1.setIs_friend(0);
            // (from - to - isFriend)
            FriendEntity friendRequest2 = new FriendEntity();
            friendRequest2.setTo(userRepository.findById(fromUser).orElse(null));
            friendRequest2.setFrom(userRepository.findById(toUser).orElse(null));
            friendRequest2.setIs_friend(0);

            // 데이터베이스에 Entity 저장
            friendRepository.saveAll(Arrays.asList(friendRequest1, friendRequest2));

            return "success";
        } catch (Exception e) {
            return "fail";
        }

    }

}
