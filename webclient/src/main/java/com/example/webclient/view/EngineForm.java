package com.example.webclient.view;

import com.example.webclient.model.Engine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EngineForm {

    private Long id;
    private String name;
    private Integer horsePower;

    public EngineForm(Engine engine) {
        id = engine.getId();
        name = engine.getName();
        horsePower = engine.getHorsePower();
    }

}
