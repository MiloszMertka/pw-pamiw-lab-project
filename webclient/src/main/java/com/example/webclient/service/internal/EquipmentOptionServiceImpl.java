package com.example.webclient.service.internal;

import com.example.webclient.model.EquipmentOption;
import com.example.webclient.service.EquipmentOptionService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentOptionServiceImpl implements EquipmentOptionService {

    private CrudService<EquipmentOption> crudService;

    @Value("${api.baseUrl}")
    private String baseUrl;

    @Value("${api.endpoints.equipmentOptions}")
    private String equipmentOptionsEndpoint;

    @PostConstruct
    private void init() {
        crudService = new CrudService<>(baseUrl);
    }

    @Override
    public List<EquipmentOption> getAllEquipmentOptions() {
        return crudService.getAll(equipmentOptionsEndpoint, EquipmentOption.class);
    }

    @Override
    public void createEquipmentOption(EquipmentOption equipmentOption) {
        crudService.create(equipmentOptionsEndpoint, equipmentOption);
    }

    @Override
    public void updateEquipmentOption(Long id, EquipmentOption equipmentOption) {
        crudService.update(equipmentOptionsEndpoint + "/" + id, equipmentOption);
    }

    @Override
    public void deleteEquipmentOption(Long id) {
        crudService.delete(equipmentOptionsEndpoint + "/" + id);
    }

}
