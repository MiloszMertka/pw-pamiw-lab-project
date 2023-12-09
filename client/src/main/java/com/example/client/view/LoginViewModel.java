package com.example.client.view;

import com.example.client.Views;
import com.example.client.model.LoginUser;
import com.example.client.service.AuthService;
import com.example.client.service.AuthStateService;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginViewModel {

    private final AuthService authService;
    private final AuthStateService authStateService;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private VBox content;

    @FXML
    private VBox progressIndicator;

    @Inject
    public LoginViewModel(AuthService authService, AuthStateService authStateService) {
        this.authService = authService;
        this.authStateService = authStateService;
    }

    @FXML
    private void onGoBackButtonClick(ActionEvent event) {
        loadScene(Views.UNAUTHENTICATED_VIEW, event);
    }

    @FXML
    private void onLoginButtonClick(ActionEvent event) {
        content.setDisable(true);
        progressIndicator.setVisible(true);

        final var username = usernameTextField.getText();
        final var password = passwordField.getText();
        final var loginUser = new LoginUser(username, password);
        final var jwt = authService.login(loginUser);

        if (jwt == null) {
            progressIndicator.setVisible(false);
            content.setDisable(false);
            return;
        }

        authStateService.storeJwtToken(jwt.token());
        loadScene(Views.APP_VIEW, event);

        progressIndicator.setVisible(false);
        content.setDisable(false);
    }

    private void loadScene(Views view, ActionEvent event) {
        final var source = (Node) event.getSource();
        final var stage = (Stage) source.getScene().getWindow();
        view.loadScene(stage);
    }

}
