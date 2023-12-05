package com.example.client.view;

import com.example.client.Views;
import com.example.client.model.RegisterUser;
import com.example.client.service.AuthService;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrationViewModel {

    private final AuthService authService;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @Inject
    public RegistrationViewModel(AuthService authService) {
        this.authService = authService;
    }

    @FXML
    private void onGoBackButtonClick(ActionEvent event) {
        returnToUnauthorizedView(event);
    }

    @FXML
    private void onRegisterButtonClick(ActionEvent event) {
        final var username = usernameTextField.getText();
        final var password = passwordTextField.getText();
        final var registerUser = new RegisterUser(username, password);
        authService.register(registerUser);
        returnToUnauthorizedView(event);
    }

    private void returnToUnauthorizedView(ActionEvent event) {
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        Views.UNAUTHENTICATED_VIEW.loadScene(stage);
    }

}
