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

    public Integer getIdx() { return idx; }
    public void setIdx(Integer idx) { this.idx = idx; }
    public UserEntity getFrom() { return from; }

    public void setFrom(UserEntity from) { this.from = from; }
    public UserEntity getTo() { return to; }
    public void setTo(UserEntity to) { this.to = to; }

}
