package com.planisjustnow.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "friend_list")
public class FriendEntity {
    @Id
    // 다대다?
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private String from;

    // 다대다?
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private String to;

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public FriendEntity(String from, String to) {
        this.from = from;
        this.to = to;
    }
}
