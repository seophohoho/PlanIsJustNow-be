package com.planisjustnow.data.dto;

import com.planisjustnow.data.entity.PetEntity;

public class ChoicePetDto {
    Integer species;
    String nickname;

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
