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
import java.util.List;
import java.util.Optional;

@Service
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String friendRequest(FriendDto friendDto) {
        try{
            String fromUser = friendDto.getFrom();
            String toUser = friendDto.getTo();

            // 클라이언트로부터 받은 사용자 2명의 정보 등록
            Optional<UserEntity> fromUserEntity = userRepository.findById(fromUser);
            Optional<UserEntity> toUserEntity = userRepository.findById(toUser);

            // 요청 정보를 기반으로 새로운 친구 요청 Entity 생성하기 (from-to)
            FriendEntity friendRequest1 = new FriendEntity();
            friendRequest1.setFrom(fromUserEntity.get());
            friendRequest1.setTo(toUserEntity.get());
            friendRequest1.setIs_friend(0);
            friendRepository.save(friendRequest1);
            // 요청 정보를 기반으로 새로운 친구 요청 Entity 생성하기 (to-from)
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

    @Transactional
    public String requestAccept(FriendDto friendDto) {
        try {
            String fromUser = friendDto.getFrom();
            String toUser = friendDto.getTo();

            // 친구 요청을 보낸 사용자와 요청을 받은 사용자의 Entity 확인
            Optional<UserEntity> fromUserEntity = userRepository.findById(fromUser);
            Optional<UserEntity> toUserEntity = userRepository.findById(toUser);

            // 사용자 정보가 없을 경우, 요청 실패 처리
            if (!fromUserEntity.isPresent() || !toUserEntity.isPresent()) {
                return "fail";
            }

            // 친구 요청목록 조회
            List<FriendEntity> friendRequest1 = friendRepository.findByFromAndTo(fromUserEntity.get(), toUserEntity.get());
            List<FriendEntity> friendRequest2 = friendRepository.findByFromAndTo(toUserEntity.get(), fromUserEntity.get());

            // 친구 수락 (is_friend의 값을 1로 변경)
            for (FriendEntity friendRequest : friendRequest1) {
                friendRequest.setIs_friend(1);
                friendRepository.save(friendRequest);
            }
            for (FriendEntity friendRequest : friendRequest2) {
                friendRequest.setIs_friend(1);
                friendRepository.save(friendRequest);
            }
            return "success";

        } catch (Exception e) {
            return "fail";
        }
    }

}
