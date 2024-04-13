package com.planisjustnow.service;

import com.planisjustnow.data.dto.FriendDto;
import com.planisjustnow.data.entity.FriendEntity;
import com.planisjustnow.data.repository.FriendRepository;
import com.planisjustnow.data.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

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
        FriendEntity friendRequest = new FriendEntity();
        friendRequest.setFrom(userRepository.findById(fromUser).orElse(null));
        friendRequest.setTo(userRepository.findById(toUser).orElse(null));
        friendRequest.setIs_friend(0);
    }

}
