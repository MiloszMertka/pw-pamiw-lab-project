package com.example.client.view;

import com.example.client.Views;
import com.example.client.model.EquipmentOption;
import com.example.client.service.EquipmentOptionService;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EquipmentOptionFormViewModel implements PayloadViewModel<EquipmentOption> {

    private final EquipmentOptionService equipmentOptionService;

    @FXML
    private TextField nameTextField;

    private Long id = null;

    @Inject
    public EquipmentOptionFormViewModel(EquipmentOptionService equipmentOptionService) {
        this.equipmentOptionService = equipmentOptionService;
    }

    @Override
    public void processPayload(EquipmentOption equipmentOption) {
        id = equipmentOption.id();
        nameTextField.setText(equipmentOption.name());
    }

    @FXML
    private void onGoBackButtonClick(ActionEvent event) {
        returnToEquipmentOptionsView(event);
    }

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        final var equipmentOption = prepareEquipmentOptionData();

        if (id == null) {
            equipmentOptionService.createEquipmentOption(equipmentOption);
        } else {
            equipmentOptionService.updateEquipmentOption(id, equipmentOption);
        }

        returnToEquipmentOptionsView(event);
    }

    private void returnToEquipmentOptionsView(ActionEvent event) {
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        Views.EQUIPMENT_OPTIONS_VIEW.loadScene(stage);
    }

    private EquipmentOption prepareEquipmentOptionData() {
        final var name = nameTextField.getText();
        return new EquipmentOption(id, name);
    }

}
