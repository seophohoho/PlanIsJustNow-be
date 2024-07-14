package com.planisjustnow.data.dto;

public class PasswordResetDto {
    String userId;
    String password;

    // Getter, Setter 메서드
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
