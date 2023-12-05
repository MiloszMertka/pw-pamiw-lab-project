package com.example.client.service;

import com.example.client.model.Engine;

import java.util.List;

public interface EngineService {

    List<Engine> getAllEngines();

    void createEngine(Engine engine);

    void updateEngine(Long id, Engine engine);

    void deleteEngine(Long id);

}
