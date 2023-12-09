package com.example.client.view;

import atlantafx.base.controls.ToggleSwitch;
import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import com.example.client.Views;
import com.example.client.service.AppStateService;
import com.google.inject.Inject;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class SettingsViewModel implements Initializable {

    private final AppStateService appStateService;

    @FXML
    private ToggleSwitch darkModeToggleSwitch;

    @FXML
    private ComboBox<String> languageComboBox;

    @Inject
    public SettingsViewModel(AppStateService appStateService) {
        this.appStateService = appStateService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        darkModeToggleSwitch.setSelected(appStateService.getIsDarkMode());
        darkModeToggleSwitch.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            appStateService.storeIsDarkMode(newValue);
            changeTheme(newValue);
        });
        languageComboBox.getSelectionModel().select(appStateService.getLanguageCode().toUpperCase());
        languageComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            appStateService.storeLanguageCode(newValue);
            changeLanguage(newValue);
        });
    }

    @FXML
    private void onGoBackButtonClick(ActionEvent event) {
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        Views.APP_VIEW.loadScene(stage);
    }

    private void changeTheme(boolean isDarkMode) {
        final var theme = isDarkMode ? new PrimerDark() : new PrimerLight();
        Application.setUserAgentStylesheet(theme.getUserAgentStylesheet());
    }

    private void changeLanguage(String languageCode) {
        final var locale = new Locale(languageCode.toLowerCase());
        final var resourceBundle = ResourceBundle.getBundle("com.example.client.translation", locale);
        Views.setResourceBundle(resourceBundle);
        Views.SETTINGS_VIEW.loadScene((Stage) languageComboBox.getScene().getWindow());
    }

}
