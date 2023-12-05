package com.example.client.view;

import com.example.client.Views;
import com.example.client.service.AuthStateService;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class AppViewModel {

    private final AuthStateService authStateService;

    @Inject
    public AppViewModel(AuthStateService authStateService) {
        this.authStateService = authStateService;
    }

    @FXML
    private void onWeatherButtonClick(ActionEvent event) {
        loadScene(Views.WEATHER_VIEW, event);
    }

    @FXML
    private void onCarsButtonClick(ActionEvent event) {
        loadScene(Views.CARS_VIEW, event);
    }

    @FXML
    private void onEnginesButtonClick(ActionEvent event) {
        loadScene(Views.ENGINES_VIEW, event);
    }

    @FXML
    private void onEquipmentOptionsButtonClick(ActionEvent event) {
        loadScene(Views.EQUIPMENT_OPTIONS_VIEW, event);
    }

    @FXML
    private void onLogoutButtonClick(ActionEvent event) {
        authStateService.clearJwtToken();
        loadScene(Views.UNAUTHENTICATED_VIEW, event);
    }

    private void loadScene(Views view, ActionEvent event) {
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        view.loadScene(stage);
    }

}
