package com.example.client.view;

import com.example.client.Views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class UnauthenticatedViewModel {

    @FXML
    private void onLoginButtonClick(ActionEvent event) {
        loadScene(Views.LOGIN_VIEW, event);
    }

    @FXML
    private void onRegisterButtonClick(ActionEvent event) {
        loadScene(Views.REGISTER_VIEW, event);
    }

    private void loadScene(Views view, ActionEvent event) {
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        view.loadScene(stage);
    }

}
