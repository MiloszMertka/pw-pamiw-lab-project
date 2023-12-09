package com.example.client.view;

import com.example.client.Views;
import com.example.client.model.Engine;
import com.example.client.service.EngineService;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EngineFormViewModel implements PayloadViewModel<Engine> {

    private final EngineService engineService;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField horsePowerTextField;

    @FXML
    private VBox content;

    @FXML
    private VBox progressIndicator;

    private Long id = null;

    @Inject
    public EngineFormViewModel(EngineService engineService) {
        this.engineService = engineService;
    }

    @Override
    public void processPayload(Engine engine) {
        id = engine.id();
        nameTextField.setText(engine.name());
        horsePowerTextField.setText(engine.horsePower().toString());
    }

    @FXML
    private void onGoBackButtonClick(ActionEvent event) {
        returnToEnginesView(event);
    }

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        content.setDisable(true);
        progressIndicator.setVisible(true);

        final var engine = prepareEngineData();

        if (id == null) {
            engineService.createEngine(engine);
        } else {
            engineService.updateEngine(id, engine);
        }

        returnToEnginesView(event);

        progressIndicator.setVisible(false);
        content.setDisable(false);
    }

    private void returnToEnginesView(ActionEvent event) {
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        Views.ENGINES_VIEW.loadScene(stage);
    }

    private Engine prepareEngineData() {
        final var name = nameTextField.getText();
        final var horsePower = Integer.parseInt(horsePowerTextField.getText());
        return new Engine(id, name, horsePower);
    }

}
