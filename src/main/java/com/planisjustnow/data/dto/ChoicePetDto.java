package com.planisjustnow.data.dto;

import com.planisjustnow.data.entity.PetEntity;

public class ChoicePetDto {
    String email;
    Integer species;
    String nickname;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSpecies() {
        return species;
    }

    public void setSpecies(Integer species) {
        this.species = species;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
