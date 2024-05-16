package com.planisjustnow.data.entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;


@Entity
@Table(name = "user_info")
public class UserEntity {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "password")
    private String password;

    @Column(name = "todolist_failure_count")
    private int todolistFailureCount;

    @Column(name = "image")
    private String image;
//    @OneToMany(mappedBy = "userId")
//    private List<UserPetEntity> userEntities = new ArrayList<UserPetEntity>();

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public int getTodolistFailureCount() {
        return todolistFailureCount;
    }

    public String getImageUrl() {
        return image;
    }

    public void setImageUrl(String imageUrl) {
        this.image = imageUrl;
    }

    public UserEntity(){}
    public UserEntity(String username, String email, String password, String nickname, int todolistFailureCount){ //DB 구조가 바뀌면서 충돌 예외처리
        this.username = username;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.todolistFailureCount = todolistFailureCount;
    }

    public UserEntity(String email, String password, String nickname, int todolistFailureCount){ //DB 구조가 바뀌면서 충돌 예외처리
        this.username = "test";
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.todolistFailureCount = todolistFailureCount;
    }
}