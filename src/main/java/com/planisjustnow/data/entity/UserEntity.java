package com.planisjustnow.data.entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;


@Entity
@Table(name = "tUser")
public class UserEntity {
    @Id
    @Column(name = "username",nullable = false)
    private String username;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "nickname",nullable = false)
    private String nickname;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "todolist_failure_count",nullable = false)
    private int todolistFailureCount;

    @Column(name = "image",nullable = false)
    private String image;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTodolistFailureCount() {
        return todolistFailureCount;
    }

    public void setTodolistFailureCount(int todolistFailureCount) {
        this.todolistFailureCount = todolistFailureCount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public UserEntity(){}
    public UserEntity(String username, String email, String password, String nickname, int todolistFailureCount,String image) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.todolistFailureCount = todolistFailureCount;
        this.image = image;
    }
}
