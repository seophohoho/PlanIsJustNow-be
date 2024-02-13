package com.planisjustnow.data.entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pet_info")
public class PetEntity {
    @Id
    @Column(name="id")
    private Integer petId;
    @Column(name="species")
    private String species;
    @OneToMany(mappedBy = "petId")
    private List<UserPetEntity> petEntities = new ArrayList<UserPetEntity>();
    public Integer getPetId() {
        return petId;
    }
    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }
}
