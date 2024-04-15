package com.planisjustnow.data.repository;

import com.planisjustnow.data.entity.FriendEntity;
import com.planisjustnow.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<FriendEntity, Integer> {
    List<FriendEntity> findByFromAndTo(UserEntity from, UserEntity to);
    @Query("SELECT f FROM FriendEntity f WHERE f.from = :from AND f.to = :to AND f.is_friend = :isFriend")
    List<FriendEntity> findByFromAndToAndIsFriend(@Param("from") UserEntity from, @Param("to") UserEntity to, @Param("isFriend") Integer isFriend);



}
