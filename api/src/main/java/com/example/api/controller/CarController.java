package com.example.api.controller;

import com.example.api.dto.CarDto;
import com.example.api.service.CarUseCases;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
class CarController {

    private final CarUseCases carUseCases;

    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCars() {
        final var carDtos = carUseCases.getAllCars();
        return ResponseEntity.ok(carDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCar(@PathVariable Long id) {
        final var carDto = carUseCases.getCar(id);
        return ResponseEntity.ok(carDto);
    }

    @PostMapping
    public ResponseEntity<Void> createCar(@RequestBody @Valid CarDto carDto) {
        carUseCases.createCar(carDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCar(@PathVariable Long id, @RequestBody @Valid CarDto carDto) {
        carUseCases.updateCar(id, carDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carUseCases.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

}
