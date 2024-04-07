package com.planisjustnow.service;

import com.planisjustnow.data.repository.FriendRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

public class FriendService {

    @Autowired
    private FriendRepository friendRepository;

}
