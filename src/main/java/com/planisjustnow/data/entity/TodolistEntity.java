package com.planisjustnow.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "todolist")
public class TodolistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Integer idx;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userId;
    @Column(name = "title")
    private String title;
    @Column(name = "start_date")
    private String startDate;
    @Column(name = "time")
    private String time;
    @Column(name = "is_important")
    private Integer isImportant;
    @Column(name = "is_complete")
    private Integer isComplete;
    public Integer getIsImportant() {
        return isImportant;
    }
    public void setIsImportant(Integer isImportant) {
        this.isImportant = isImportant;
    }
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
    public String getStartDate() {return startDate;}
    public void setStartDate(String startDate) {this.startDate = startDate;}
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public Integer getIsComplete() {
        return isComplete;
    }
    public void setIsComplete(Integer isComplete) {
        this.isComplete = isComplete;
    }
    TodolistEntity(){}
    public TodolistEntity(UserEntity userId,String title,String startDate,String time, Integer isImportant,Integer isComplete){
        this.userId = userId;
        this.title = title;
        this.startDate = startDate;
        this.time = time;
        this.isImportant = isImportant;
        this.isComplete = isComplete;
    }
}