package com.example.webclient.view;

import com.example.webclient.model.Car;
import com.example.webclient.model.EquipmentOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarForm {

    private Long id;
    private String name;
    private String color;
    private Long engineId;
    private List<Long> equipmentOptionsIds;

    public CarForm(Car car) {
        id = car.getId();
        name = car.getName();
        color = car.getColor();
        engineId = car.getEngine().getId();
        equipmentOptionsIds = car.getEquipmentOptions().stream().map(EquipmentOption::getId).toList();
    }

}
