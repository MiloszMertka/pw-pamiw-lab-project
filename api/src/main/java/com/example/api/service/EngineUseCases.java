package com.example.api.service;

import com.example.api.dto.EngineDto;

import java.util.List;

public interface EngineUseCases {

    List<EngineDto> getAllEngines();

    EngineDto getEngine(Long id);

    void createEngine(EngineDto engineDto);

    void updateEngine(Long id, EngineDto engineDto);

    void deleteEngine(Long id);

}
