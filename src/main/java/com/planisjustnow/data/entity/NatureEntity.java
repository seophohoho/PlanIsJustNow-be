package com.planisjustnow.data.entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "nature_info")
public class NatureEntity {
    @Id
    @Column(name = "id")
    private Integer natureId;
    @Column(name = "name")
    private String name;
    @Column(name = "bonus_increase")
    private int bonusIncrease;
    @Column(name = "bonus_drop")
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