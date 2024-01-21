package com.planisjustnow.data.repository;

import com.planisjustnow.data.entity.NatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NatureRepository extends JpaRepository<NatureEntity, Integer> {
    NatureEntity findByNatureId(Integer natureId);
}
