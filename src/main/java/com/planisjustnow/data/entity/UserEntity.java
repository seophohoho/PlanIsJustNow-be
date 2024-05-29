package com.planisjustnow.data.entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_info")
public class UserEntity {
    @Id
    @Column(name="id")
    String email;
    @Column(name="password")
    String password;
    @Column(name="nickname")
    String nickname;
    @Column(name="todolist_failure_count")
    int todolistFailureCount;
    @Column(name="image")
    String imageUrl;
    @OneToMany(mappedBy = "userId")
    private List<UserPetEntity> userEntities = new ArrayList<UserPetEntity>();

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
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UserEntity(){}
    public UserEntity(String email, String password, String nickname, int todolistFailureCount, String imageUrl){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.todolistFailureCount = todolistFailureCount;
        this.imageUrl = imageUrl;
    }
}