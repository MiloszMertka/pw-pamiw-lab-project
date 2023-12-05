package com.example.webclient.service;

import com.example.webclient.model.Engine;

import java.util.List;

public interface EngineService {

    List<Engine> getAllEngines();

    void createEngine(Engine engine);

    void updateEngine(Long id, Engine engine);

    void deleteEngine(Long id);

}
