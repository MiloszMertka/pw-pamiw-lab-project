package com.example.client;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String APP_TITLE = "PW PAMIW Lab";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle(APP_TITLE);
        Views.UNAUTHENTICATED_VIEW.loadScene(stage);
        stage.show();
    }

}