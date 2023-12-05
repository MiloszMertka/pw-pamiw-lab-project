package com.example.webclient.view;

import com.example.webclient.model.EquipmentOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentOptionForm {

    private Long id;
    private String name;

    public EquipmentOptionForm(EquipmentOption equipmentOption) {
        id = equipmentOption.getId();
        name = equipmentOption.getName();
    }

}
