package com.example.api.mapper;

import com.example.api.dto.EngineDto;
import com.example.api.model.Engine;

public interface EngineMapper {

    EngineDto mapEngineToEngineDto(Engine engine);

    Engine mapEngineDtoToEngine(EngineDto engineDto);

    void updateEngineFromEngineDto(Engine engine, EngineDto engineDto);

}
