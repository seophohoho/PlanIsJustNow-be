package com.planisjustnow.service;

import com.planisjustnow.data.dto.FriendDto;
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

    }

}
