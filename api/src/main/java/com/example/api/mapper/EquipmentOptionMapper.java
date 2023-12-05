package com.example.api.mapper;

import com.example.api.dto.EquipmentOptionDto;
import com.example.api.model.EquipmentOption;

public interface EquipmentOptionMapper {

    EquipmentOptionDto mapEquipmentOptionToEquipmentOptionDto(EquipmentOption equipmentOption);

    EquipmentOption mapEquipmentOptionDtoToEquipmentOption(EquipmentOptionDto equipmentOptionDto);

    void updateEquipmentOptionFromEquipmentOptionDto(EquipmentOption equipmentOption, EquipmentOptionDto equipmentOptionDto);

}
