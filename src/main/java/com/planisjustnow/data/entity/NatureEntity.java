package com.planisjustnow.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "nature_list")
public class NatureEntity {
    @Id
    @Column(name="id")
    int id;
    @Column(name="name")
    String name;
}
