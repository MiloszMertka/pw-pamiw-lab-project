package com.example.client.model;

import java.util.List;

public record Car(
        Long id,
        String name,
        String color,
        Engine engine,
        List<EquipmentOption> equipmentOptions
) {

}
