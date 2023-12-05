package com.example.api.repository;

import com.example.api.model.EquipmentOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentOptionRepository extends JpaRepository<EquipmentOption, Long> {

    boolean existsByName(String name);

}
