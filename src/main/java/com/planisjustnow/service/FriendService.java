package com.planisjustnow.service;

import com.planisjustnow.data.dto.FriendDto;
import com.planisjustnow.data.entity.FriendEntity;
import com.planisjustnow.data.entity.UserEntity;
import com.planisjustnow.data.repository.FriendRepository;
import com.planisjustnow.data.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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

            // 친구 요청이 이미 존재할 경우, 오류 반환
            List<FriendEntity> existingRequests = friendRepository.findByFromAndTo(fromUserEntity.get(), toUserEntity.get());
            if (!existingRequests.isEmpty()) {
                return "already exist";
            } else {
                // 친구 요청이 존재하지 않을 경우, 요청 정보를 기반으로 새로운 친구 요청 Entity 생성하기
                // from-to
                FriendEntity friendRequest1 = new FriendEntity();
                friendRequest1.setFrom(fromUserEntity.get());
                friendRequest1.setTo(toUserEntity.get());
                friendRequest1.setIs_friend(0);
                friendRepository.save(friendRequest1);
                // to-from
                FriendEntity friendRequest2 = new FriendEntity();
                friendRequest2.setFrom(toUserEntity.get());
                friendRequest2.setTo(fromUserEntity.get());
                friendRequest2.setIs_friend(0);
                friendRepository.save(friendRequest2);
                return "success";
            }

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
            List<FriendEntity> friendRequest1 = friendRepository.findByFromAndToAndIsFriend(fromUserEntity.get(), toUserEntity.get(), 0);
            List<FriendEntity> friendRequest2 = friendRepository.findByFromAndToAndIsFriend(toUserEntity.get(), fromUserEntity.get(), 0);
            // 요청 정보가 없을 경우, 요청 실패 처리
            if (friendRequest1.isEmpty() && friendRequest2.isEmpty()) {
                return "fail";
            }

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

    @Transactional
    public String requestRefuse(FriendDto friendDto) {
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
            List<FriendEntity> requestRefuse1 = friendRepository.findByFromAndToAndIsFriend(fromUserEntity.get(), toUserEntity.get(), 0);
            List<FriendEntity> requestRefuse2 = friendRepository.findByFromAndToAndIsFriend(toUserEntity.get(), fromUserEntity.get(), 0);
            // 요청 정보가 없을 경우, 요청 실패 처리
            if (requestRefuse1.isEmpty() && requestRefuse2.isEmpty()) {
                return "fail";
            }

            // 친구 거절 (두 개의 해당 Entity 삭제)
            friendRepository.deleteAll(requestRefuse1);
            friendRepository.deleteAll(requestRefuse2);
            return "success";

        } catch (Exception e) {
            return "fail";
        }
    }

    @Transactional
    public String friendDelete(FriendDto friendDto) {
        try {
            String fromUser = friendDto.getFrom();
            String toUser = friendDto.getTo();

            // 서로 친구인 사용자의 Entity 확인
            Optional<UserEntity> fromUserEntity = userRepository.findById(fromUser);
            Optional<UserEntity> toUserEntity = userRepository.findById(toUser);
            // 사용자 정보가 없을 경우, 요청 실패 처리
            if (!fromUserEntity.isPresent() || !toUserEntity.isPresent()) {
                return "fail";
            }

            // 친구 요청목록 조회
            List<FriendEntity> friendDelete1 = friendRepository.findByFromAndToAndIsFriend(fromUserEntity.get(), toUserEntity.get(), 1);
            List<FriendEntity> friendDelete2 = friendRepository.findByFromAndToAndIsFriend(toUserEntity.get(), fromUserEntity.get(), 1);
            // 요청 정보가 없을 경우, 요청 실패 처리
            if (friendDelete1.isEmpty() && friendDelete2.isEmpty()) {
                return "fail";
            }

            // 친구 거절 (두 개의 해당 Entity 삭제)
            friendRepository.deleteAll(friendDelete1);
            friendRepository.deleteAll(friendDelete2);
            return "success";

        } catch (Exception e) {
            return "fail";
        }
    }

    @Transactional
    public List<String> friendInquiry(FriendDto friendDto) {
        try {
            String fromUser = friendDto.getFrom();

            // 서로 친구인 사용자의 Entity 확인
            Optional<UserEntity> fromUserEntity = userRepository.findById(fromUser);
            // 친구인 사용자가 없을 경우, 빈 리스트 반환
            if (!fromUserEntity.isPresent()) {
                return Collections.emptyList();
            }

            // 친구 목록에 존재하는지(친구여부) 조회 (is_friend가 1이어여지 친구라는 의미이므로 조회 가능)
            List<FriendEntity> friends = friendRepository.findFriendsByFromAndIsFriend(fromUserEntity.get(), 1);
            // 친구 유저의 이메일만 추출하여 리스트에 담고 반환
            List<String> friendEmails = new ArrayList<>();
            for (FriendEntity friend : friends) {
                friendEmails.add(friend.getTo().getEmail());
            }
            return friendEmails;

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

}
