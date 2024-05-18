package com.planisjustnow.data.entity;

import jakarta.persistence.*;
import org.checkerframework.checker.units.qual.C;

@Entity
@Table(name="friend")
public class FriendEntity {
    @Id
    @Column(name="idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;
    @ManyToOne
    @JoinColumn(name = "from_user", referencedColumnName = "id")
    private UserEntity fromUser;
    @ManyToOne
    @JoinColumn(name = "to_user", referencedColumnName = "id")
    private UserEntity toUser;
    @Column(name="is_friend")
    private int isFriend;
    @Column(name="start_date")
    private String startDate;

    public long getIdx() {
        return idx;
    }

    public void setIdx(long idx) {
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
