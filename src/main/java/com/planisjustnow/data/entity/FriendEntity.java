package com.planisjustnow.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "friend_list")
public class FriendEntity {

    @Id
    @Column(name="idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity from;

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity to;

    @Column(name = "is_friend")
    private Integer is_friend;

    public Integer getIdx() { return idx; }
    public void setIdx(Integer idx) { this.idx = idx; }
    public UserEntity getFrom() { return from; }
    public Integer getIs_friend() { return is_friend; }

    public void setFrom(UserEntity from) { this.from = from; }
    public UserEntity getTo() { return to; }
    public void setTo(UserEntity to) { this.to = to; }
    public void setIs_friend(Integer is_friend) { this.is_friend = is_friend; }

    public FriendEntity(UserEntity id, Integer is_friend) {
        this.from = id;
        this.to = id;
        this.is_friend = is_friend;
    }
}