package com.planisjustnow.data.dto;

public class PasswordResetVerificationDto {
    String email;
    String code;

    // Getter, Setter 메서드
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
