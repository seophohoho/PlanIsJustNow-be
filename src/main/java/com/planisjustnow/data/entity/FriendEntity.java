package com.planisjustnow.data.entity;

import jakarta.persistence.*;
import org.checkerframework.checker.units.qual.C;

@Entity
@Table(name="tFriend")
public class FriendEntity {
    @Id
    @Column(name="idx",nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;
    @ManyToOne
    @JoinColumn(name = "fromUser", referencedColumnName = "username",nullable = false)
    private UserEntity fromUser;
    @ManyToOne
    @JoinColumn(name = "toUser", referencedColumnName = "username",nullable = false)
    private UserEntity toUser;
    @Column(name="is_friend",nullable = false)
    private int isFriend;
    @Column(name="start_date",nullable = false)
    private String startDate;

    public long getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public UserEntity getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserEntity fromUser) {
        this.fromUser = fromUser;
    }

    public UserEntity getToUser() {
        return toUser;
    }

    public void setToUser(UserEntity toUser) {
        this.toUser = toUser;
    }

    public int getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public FriendEntity(){}
    public FriendEntity(UserEntity fromUser, UserEntity toUser, int isFriend,String startDate){
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.isFriend = isFriend;
        this.startDate = startDate;
    }
}
