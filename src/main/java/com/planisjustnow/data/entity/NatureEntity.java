package com.planisjustnow.data.entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tNatureInfo")
public class NatureEntity {
    @Id
    @Column(name = "id",nullable = false)
    private Integer natureId;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "bonus_increase",nullable = false)
    private int bonusIncrease;
    @Column(name = "bonus_drop",nullable = false)
    private int bonusDrop;

    @OneToMany(mappedBy = "natureId")
    private List<UserPetEntity> natureEntities = new ArrayList<UserPetEntity>();

    public Integer getNatureId() {
        return natureId;
    }

    public void setNatureId(Integer natureId) {
        this.natureId = natureId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBonusIncrease() {
        return bonusIncrease;
    }

    public void setBonusIncrease(int bonusIncrease) {
        this.bonusIncrease = bonusIncrease;
    }

    public int getBonusDrop() {
        return bonusDrop;
    }

    public void setBonusDrop(int bonusDrop) {
        this.bonusDrop = bonusDrop;
    }
}