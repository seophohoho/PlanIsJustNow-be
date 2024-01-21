package com.planisjustnow.data.repository;

import com.planisjustnow.data.entity.UserPetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPetRepository extends JpaRepository<UserPetEntity, Integer> {
}
