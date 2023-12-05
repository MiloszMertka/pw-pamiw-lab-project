package com.example.webclient.service.internal;

import com.example.webclient.model.Engine;
import com.example.webclient.service.EngineService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EngineServiceImpl implements EngineService {

    private CrudService<Engine> crudService;

    @Value("${api.baseUrl}")
    private String baseUrl;

    @Value("${api.endpoints.engines}")
    private String enginesEndpoint;

    @PostConstruct
    private void init() {
        crudService = new CrudService<>(baseUrl);
    }

    @Override
    public List<Engine> getAllEngines() {
        return crudService.getAll(enginesEndpoint, Engine.class);
    }

    @Override
    public void createEngine(Engine engine) {
        crudService.create(enginesEndpoint, engine);
    }

    @Override
    public void updateEngine(Long id, Engine engine) {
        crudService.update(enginesEndpoint + "/" + id, engine);
    }

    @Override
    public void deleteEngine(Long id) {
        crudService.delete(enginesEndpoint + "/" + id);
    }

}
