package com.example.api.controller;

import com.example.api.dto.EngineDto;
import com.example.api.service.EngineUseCases;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/engines")
@RequiredArgsConstructor
class EngineController {

    private final EngineUseCases engineUseCases;

    @GetMapping
    public ResponseEntity<List<EngineDto>> getAllEngines() {
        final var engineDtos = engineUseCases.getAllEngines();
        return ResponseEntity.ok(engineDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EngineDto> getEngineById(@PathVariable Long id) {
        final var engineDto = engineUseCases.getEngine(id);
        return ResponseEntity.ok(engineDto);
    }

    @PostMapping
    public ResponseEntity<Void> createEngine(@RequestBody @Valid EngineDto engineDto) {
        engineUseCases.createEngine(engineDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEngine(@PathVariable Long id, @RequestBody @Valid EngineDto engineDto) {
        engineUseCases.updateEngine(id, engineDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEngine(@PathVariable Long id) {
        engineUseCases.deleteEngine(id);
        return ResponseEntity.noContent().build();
    }

}
