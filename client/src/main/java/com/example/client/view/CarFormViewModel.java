package com.example.client.view;

import com.example.client.Views;
import com.example.client.model.Car;
import com.example.client.model.Engine;
import com.example.client.model.EquipmentOption;
import com.example.client.service.CarService;
import com.example.client.service.EngineService;
import com.example.client.service.EquipmentOptionService;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CarFormViewModel implements Initializable, PayloadViewModel<Car> {

    private final CarService carService;
    private final EngineService engineService;
    private final EquipmentOptionService equipmentOptionService;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField colorTextField;

    @FXML
    private ComboBox<Engine> engineComboBox;

    @FXML
    private CheckComboBox<EquipmentOption> equipmentCheckComboBox;

    private Long id = null;

    @Inject
    public CarFormViewModel(CarService carService, EngineService engineService, EquipmentOptionService equipmentOptionService) {
        this.carService = carService;
        this.engineService = engineService;
        this.equipmentOptionService = equipmentOptionService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final var engines = FXCollections.observableArrayList(engineService.getAllEngines());
        engineComboBox.setItems(engines);
        final var equipmentOptions = FXCollections.observableArrayList(equipmentOptionService.getAllEquipmentOptions());
        equipmentCheckComboBox.getItems().addAll(equipmentOptions);
    }

    @Override
    public void processPayload(Car car) {
        id = car.id();
        nameTextField.setText(car.name());
        colorTextField.setText(car.color());
        engineComboBox.setValue(car.engine());
        car.equipmentOptions().forEach(equipmentCheckComboBox.getCheckModel()::check);
    }

    @FXML
    private void onGoBackButtonClick(ActionEvent event) {
        returnToCarsView(event);
    }

    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        final var car = prepareCarData();

        if (id == null) {
            carService.createCar(car);
        } else {
            carService.updateCar(id, car);
        }

        returnToCarsView(event);
    }

    private void returnToCarsView(ActionEvent event) {
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        Views.CARS_VIEW.loadScene(stage);
    }

    private Car prepareCarData() {
        final var name = nameTextField.getText();
        final var color = colorTextField.getText();
        final var engine = engineComboBox.getValue();
        final var equipmentOptions = equipmentCheckComboBox.getCheckModel().getCheckedItems();
        return new Car(id, name, color, engine, equipmentOptions);
    }

}
