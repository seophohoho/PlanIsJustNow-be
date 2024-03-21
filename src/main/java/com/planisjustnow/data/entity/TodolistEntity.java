package com.planisjustnow.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "todolist")
public class TodolistEntity {
    @Id
    @Column(name = "idx")
    private Integer idx;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userId;
    @Column(name = "title")
    private String title;
    @Column(name = "date")
    private String date;
    @Column(name = "time")
    private String time;
    @Column(name = "important")
    private Integer important;

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }
    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getImportant() {
        return important;
    }

    public void setImportant(Integer important) {
        this.important = important;
    }
}
