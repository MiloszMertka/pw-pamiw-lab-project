package com.example.client.model;

public record EquipmentOption(
        Long id,
        String name
) {

    @Override
    public String toString() {
        return name;
    }

}
