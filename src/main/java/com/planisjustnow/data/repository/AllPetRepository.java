package com.planisjustnow.data.repository;

import com.planisjustnow.data.entity.AllPetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllPetRepository extends JpaRepository<AllPetEntity,Integer> {

}
