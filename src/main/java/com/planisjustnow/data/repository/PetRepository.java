package com.planisjustnow.data.repository;

import com.planisjustnow.data.entity.PetEntity;
import com.planisjustnow.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<PetEntity,Integer> {
    PetEntity findByPetId(Integer petId);
}
