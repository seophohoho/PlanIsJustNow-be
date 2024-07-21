package com.planisjustnow.data.dto;

public class PasswordResetDto {
    String userId;
    String nowPassword;
    String newPassword;

    // Getter, Setter 메서드
    public String getNowPassword() {
        return nowPassword;
    }

    public void setNowPassword(String password) {
        this.nowPassword = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String password) {
        this.newPassword = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
