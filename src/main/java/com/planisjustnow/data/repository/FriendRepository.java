package com.planisjustnow.data.repository;

import com.planisjustnow.data.entity.FriendEntity;
import com.planisjustnow.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<FriendEntity, Integer> {
    List<FriendEntity> findByFromAndTo(UserEntity from, UserEntity to);
}
