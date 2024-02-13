package com.planisjustnow.data.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "user_pet")
public class UserPetEntity {
    @Id
    @Column(name="idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userId;
    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private PetEntity petId;
    @ManyToOne
    @JoinColumn(name = "nature_id", referencedColumnName = "id")
    private NatureEntity natureId;
    @Column(name="name")
    private String petName;
    @Column(name="max_friendship")
    private int maxFriendship;
    @Column(name="current_friendship")
    private int currentFriendship;
    @Column(name="run_way_count")
    private int runWayCount;

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public PetEntity getPetId() {
        return petId;
    }

    public void setPetId(PetEntity petId) {
        this.petId = petId;
    }

    public NatureEntity getNatureId() {
        return natureId;
    }

    public void setNatureId(NatureEntity natureId) {
        this.natureId = natureId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public int getMaxFriendship() {
        return maxFriendship;
    }

    public void setMaxFriendship(int maxFriendship) {
        this.maxFriendship = maxFriendship;
    }

    public int getCurrentFriendship() {
        return currentFriendship;
    }

    public void setCurrentFriendship(int currentFriendship) {
        this.currentFriendship = currentFriendship;
    }

    public int getRunWayCount() {
        return runWayCount;
    }

    public void setRunWayCount(int runWayCount) {
        this.runWayCount = runWayCount;
    }
}
