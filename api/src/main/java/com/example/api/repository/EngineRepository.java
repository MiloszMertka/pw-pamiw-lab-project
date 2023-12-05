package com.example.api.repository;

import com.example.api.model.Engine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EngineRepository extends JpaRepository<Engine, Long> {

    boolean existsByName(String name);

}
