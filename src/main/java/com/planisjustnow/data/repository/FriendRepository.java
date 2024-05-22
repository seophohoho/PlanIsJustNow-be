package com.planisjustnow.data.repository;

import com.planisjustnow.data.entity.FriendEntity;
import com.planisjustnow.data.entity.TodolistEntity;
import com.planisjustnow.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<FriendEntity, Long> {
    @Query("SELECT t FROM FriendEntity t WHERE t.fromUser = :user AND t.isFriend = 0")
    List<FriendEntity> findAllRequestFriend(@Param("user") UserEntity user);
    @Query("SELECT t FROM FriendEntity t WHERE t.fromUser = :user AND t.toUser = :target AND t.isFriend = 0")
    Optional<FriendEntity> findByFriendRequestInfo(@Param("user") UserEntity user, @Param("target") UserEntity target);
    @Transactional
    @Modifying
    @Query("DELETE FROM FriendEntity t WHERE t.fromUser = :user AND t.toUser =:target AND t.isFriend = 0")
    void deleteFriendRequest(@Param("user") UserEntity user, @Param("target") UserEntity target);
    @Query("SELECT t FROM FriendEntity t WHERE t.fromUser = :user AND t.isFriend = 1")
    List<FriendEntity> findAllRealFriend(@Param("user") UserEntity user);
    @Transactional
    @Modifying
    @Query("DELETE FROM FriendEntity t WHERE t.fromUser = :userA AND t.toUser =:userB AND t.isFriend = 1")
    void deleteFriend(@Param("userA") UserEntity userA, @Param("userB") UserEntity userB);
    @Query("SELECT EXISTS(SELECT 1 FROM FriendEntity t WHERE t.fromUser = :target AND t.toUser =:user AND t.isFriend = 0)")
    boolean isExistRequest(@Param("user") UserEntity user, @Param("target") UserEntity target);
    @Query("SELECT EXISTS(SELECT 1 FROM FriendEntity t WHERE t.fromUser = :user AND t.toUser =:target AND t.isFriend = 0)")
    boolean isExistTargetRequest(@Param("user") UserEntity user, @Param("target") UserEntity target);
    @Query("SELECT EXISTS(SELECT 1 FROM FriendEntity t WHERE t.fromUser = :user AND t.toUser =:target AND t.isFriend = 1)")
    boolean isExist(@Param("user") UserEntity user, @Param("target") UserEntity target);

}