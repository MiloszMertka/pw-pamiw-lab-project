package com.example.client.view;

import com.example.client.Views;
import com.example.client.service.AppStateService;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class AppViewModel {

    private final AppStateService appStateService;

    @Inject
    public AppViewModel(AppStateService appStateService) {
        this.appStateService = appStateService;
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
    private void onSettingsButtonClick(ActionEvent event) {
        loadScene(Views.SETTINGS_VIEW, event);
    }

    @FXML
    private void onLogoutButtonClick(ActionEvent event) {
        appStateService.clearJwtToken();
        loadScene(Views.UNAUTHENTICATED_VIEW, event);
    }

    private void loadScene(Views view, ActionEvent event) {
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        view.loadScene(stage);
    }

}
