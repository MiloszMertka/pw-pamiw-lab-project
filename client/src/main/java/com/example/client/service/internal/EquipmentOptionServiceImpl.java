package com.example.client.service.internal;

import com.example.client.Endpoints;
import com.example.client.model.EquipmentOption;
import com.example.client.service.AuthStateService;
import com.example.client.service.EquipmentOptionService;
import com.google.inject.Inject;

import java.util.List;

public class EquipmentOptionServiceImpl implements EquipmentOptionService {

    private static final String EQUIPMENT_OPTIONS_ENDPOINT = Endpoints.EQUIPMENT_OPTIONS.getEndpoint();
    private final CrudService<EquipmentOption> crudService;

    @Inject
    public EquipmentOptionServiceImpl(AuthStateService authStateService) {
        crudService = new CrudService<>(authStateService.getJwtToken());
    }

    @Override
    public List<EquipmentOption> getAllEquipmentOptions() {
        return crudService.getAll(EQUIPMENT_OPTIONS_ENDPOINT, EquipmentOption.class);
    }

    @Override
    public void createEquipmentOption(EquipmentOption equipmentOption) {
        crudService.create(EQUIPMENT_OPTIONS_ENDPOINT, equipmentOption);
    }

    @Override
    public void updateEquipmentOption(Long id, EquipmentOption equipmentOption) {
        crudService.update(EQUIPMENT_OPTIONS_ENDPOINT + "/" + id, equipmentOption);
    }

    @Override
    public void deleteEquipmentOption(Long id) {
        crudService.delete(EQUIPMENT_OPTIONS_ENDPOINT + "/" + id);
    }

}
