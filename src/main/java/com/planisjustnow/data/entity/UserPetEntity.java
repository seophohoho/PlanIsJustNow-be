package com.planisjustnow.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_pet")
public class UserPetEntity {
    @Id
    @Column(name="idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;
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
    private int petMaxFriendship;
    @Column(name="current_friendship")
    private int petCurrentFriendship;
    @Column(name="run_way_count")
    private int petRunWayCount;
}
