package com.planisjustnow.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
    public UserEntity(){}
    public UserEntity(String email, String password, String nickname, int todolistFailureCount){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.todolistFailureCount = todolistFailureCount;
    }
}
