package com.planisjustnow.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pet_info")
public class PetEntity {
    @Id
    @Column(name="id")
    Integer petId;
    @Column(name="species")
    String species;
}
