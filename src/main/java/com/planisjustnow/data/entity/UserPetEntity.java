package com.planisjustnow.data.entity;

import jakarta.persistence.*;

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
    private Integer petMaxFriendship;
    @Column(name="current_friendship")
    private Integer petCurrentFriendship;
    @Column(name="run_way_count")
    private Integer petRunWayCount;

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public void setPetId(PetEntity petId) {
        this.petId = petId;
    }

    public void setNatureId(NatureEntity natureId) {
        this.natureId = natureId;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setPetMaxFriendship(Integer petMaxFriendship) {
        this.petMaxFriendship = petMaxFriendship;
    }

    public void setPetCurrentFriendship(Integer petCurrentFriendship) {
        this.petCurrentFriendship = petCurrentFriendship;
    }

    public void setPetRunWayCount(Integer petRunWayCount) {
        this.petRunWayCount = petRunWayCount;
    }
}
