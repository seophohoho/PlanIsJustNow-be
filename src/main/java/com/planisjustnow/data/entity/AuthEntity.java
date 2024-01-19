package com.planisjustnow.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "email_buffer")
public class AuthEntity {
    @Id
    @Column(name="email")
    private String email;
    @Column(name="code")
    private String code;

    public String getEmail() {
        return email;
    }
    public String getCode() {
        return code;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public AuthEntity(){}
    public AuthEntity(String email, String code){
        this.email = email;
        this.code = code;
    }
}
