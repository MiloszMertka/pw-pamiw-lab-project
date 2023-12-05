package com.example.api.controller;

import com.example.api.dto.EquipmentOptionDto;
import com.example.api.service.EquipmentOptionUseCases;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment-options")
@RequiredArgsConstructor
class EquipmentOptionController {

    private final EquipmentOptionUseCases equipmentOptionUseCases;

    @GetMapping
    public ResponseEntity<List<EquipmentOptionDto>> getAllEquipmentOptions() {
        final var equipmentOptions = equipmentOptionUseCases.getAllEquipmentOptions();
        return ResponseEntity.ok(equipmentOptions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentOptionDto> getEquipmentOption(@PathVariable Long id) {
        final var equipmentOption = equipmentOptionUseCases.getEquipmentOption(id);
        return ResponseEntity.ok(equipmentOption);
    }

    @PostMapping
    public ResponseEntity<Void> createEquipmentOption(@RequestBody @Valid EquipmentOptionDto equipmentOptionDto) {
        equipmentOptionUseCases.createEquipmentOption(equipmentOptionDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEquipmentOption(@PathVariable Long id, @RequestBody @Valid EquipmentOptionDto equipmentOptionDto) {
        equipmentOptionUseCases.updateEquipmentOption(id, equipmentOptionDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipmentOption(@PathVariable Long id) {
        equipmentOptionUseCases.deleteEquipmentOption(id);
        return ResponseEntity.noContent().build();
    }

}
