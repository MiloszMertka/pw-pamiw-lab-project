package com.example.client;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.List;

public class ErrorHandler {

    public static void handle(List<String> messages) {
        final var combinedMessage = String.join("\n", messages);
        final var error = new Alert(AlertType.ERROR, combinedMessage, ButtonType.OK);
        error.show();
    }

}
