package com.example.webclient.service;

import com.example.webclient.model.EquipmentOption;

import java.util.List;

public interface EquipmentOptionService {

    List<EquipmentOption> getAllEquipmentOptions();

    void createEquipmentOption(EquipmentOption equipmentOption);

    void updateEquipmentOption(Long id, EquipmentOption equipmentOption);

    void deleteEquipmentOption(Long id);

}
