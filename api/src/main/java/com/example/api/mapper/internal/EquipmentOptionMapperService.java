package com.example.api.mapper.internal;

import com.example.api.dto.EquipmentOptionDto;
import com.example.api.mapper.EquipmentOptionMapper;
import com.example.api.model.EquipmentOption;
import com.example.api.repository.EquipmentOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EquipmentOptionMapperService implements EquipmentOptionMapper {

    private final EquipmentOptionRepository equipmentOptionRepository;

    @Override
    public EquipmentOptionDto mapEquipmentOptionToEquipmentOptionDto(EquipmentOption equipmentOption) {
        return new EquipmentOptionDto(equipmentOption.getId(), equipmentOption.getName());
    }

    @Override
    public EquipmentOption mapEquipmentOptionDtoToEquipmentOption(EquipmentOptionDto equipmentOptionDto) {
        return equipmentOptionDto.id() == null
                ? new EquipmentOption(equipmentOptionDto.name())
                : equipmentOptionRepository.findById(equipmentOptionDto.id()).orElseThrow();
    }

    @Override
    public void updateEquipmentOptionFromEquipmentOptionDto(EquipmentOption equipmentOption, EquipmentOptionDto equipmentOptionDto) {
        equipmentOption.setName(equipmentOptionDto.name());
    }

}
