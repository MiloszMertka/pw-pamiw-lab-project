package com.example.client.service;

import com.example.client.model.EquipmentOption;

import java.util.List;

public interface EquipmentOptionService {

    List<EquipmentOption> getAllEquipmentOptions();

    void createEquipmentOption(EquipmentOption equipmentOption);

    void updateEquipmentOption(Long id, EquipmentOption equipmentOption);

    void deleteEquipmentOption(Long id);

}
