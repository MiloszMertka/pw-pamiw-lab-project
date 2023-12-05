package com.example.webclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    private Long id;
    private String name;
    private String color;
    private Engine engine;
    private List<EquipmentOption> equipmentOptions;

}
