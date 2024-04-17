package com.planisjustnow.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name="pet_info")
public class AllPetEntity {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;
    @Column(name="info")
    private String info;
    @Column(name="path")
    private String path;
    @Column(name="species")
    private String species;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
