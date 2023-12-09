package com.example.client;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import com.example.client.service.internal.AppStateServiceImpl;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    private static final String APP_TITLE = "PW PAMIW Lab";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        loadAppSettings();
        stage.setTitle(APP_TITLE);
        Views.UNAUTHENTICATED_VIEW.loadScene(stage);
        stage.show();
    }

    private void loadAppSettings() {
        final var appState = new AppStateServiceImpl();
        final var isDarkMode = appState.getIsDarkMode();
        final var theme = isDarkMode ? new PrimerDark() : new PrimerLight();
        Application.setUserAgentStylesheet(theme.getUserAgentStylesheet());

        final var locale = new Locale(appState.getLanguageCode().toLowerCase());
        final var resourceBundle = ResourceBundle.getBundle("com.example.client.translation", locale);
        Views.setResourceBundle(resourceBundle);
    }

}