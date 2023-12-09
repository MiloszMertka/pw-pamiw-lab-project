package com.example.client;

import com.example.client.view.PayloadViewModel;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public enum Views {

    APP_VIEW("app-view.fxml"),
    WEATHER_VIEW("weather-view.fxml"),
    CARS_VIEW("cars-view.fxml"),
    CAR_FORM_VIEW("car-form-view.fxml"),
    ENGINES_VIEW("engines-view.fxml"),
    ENGINE_FORM_VIEW("engine-form-view.fxml"),
    EQUIPMENT_OPTIONS_VIEW("equipment-options-view.fxml"),
    EQUIPMENT_OPTION_FORM_VIEW("equipment-option-form-view.fxml"),
    UNAUTHENTICATED_VIEW("unauthenticated-view.fxml"),
    LOGIN_VIEW("login-view.fxml"),
    REGISTER_VIEW("register-view.fxml"),
    SETTINGS_VIEW("settings-view.fxml");

    private static final int APP_WIDTH = 512;
    private static final int APP_HEIGHT = 768;
    private static final Injector INJECTOR = Guice.createInjector(new AppModule());
    private static ResourceBundle resourceBundle;

    private final String fxmlFileName;

    Views(String fxmlFileName) {
        this.fxmlFileName = fxmlFileName;
    }

    public static void setResourceBundle(ResourceBundle resourceBundle) {
        Views.resourceBundle = resourceBundle;
    }

    public void loadScene(Stage stage) {
        try {
            final var scene = loadViewResource();
            stage.setScene(scene);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public <T> void loadScene(Stage stage, T payload) {
        try {
            final var scene = loadViewResource(payload);
            stage.setScene(scene);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private Scene loadViewResource() throws IOException {
        final var viewResource = getViewResource();
        final var fxmlLoader = new FXMLLoader(viewResource);
        fxmlLoader.setResources(resourceBundle);
        fxmlLoader.setControllerFactory(INJECTOR::getInstance);
        return new Scene(fxmlLoader.load(), APP_WIDTH, APP_HEIGHT);
    }

    private <T> Scene loadViewResource(T payload) throws IOException {
        final var viewResource = getViewResource();
        final var fxmlLoader = new FXMLLoader(viewResource);
        fxmlLoader.setResources(resourceBundle);
        fxmlLoader.setControllerFactory(INJECTOR::getInstance);
        final Parent root = fxmlLoader.load();
        final PayloadViewModel<T> viewModel = fxmlLoader.getController();
        viewModel.processPayload(payload);
        return new Scene(root, APP_WIDTH, APP_HEIGHT);
    }

    private URL getViewResource() {
        return Views.class.getResource(fxmlFileName);
    }
}
