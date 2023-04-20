package com.example.interfaceorowan;

import com.example.interfaceorowan.controleur.Controleur;
import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        /**
         * Create a login vue and a controllers to rule the app
         */
        primaryStage.setTitle("Login");

        Controleur c = new Controleur(primaryStage);
    }

    public static void main(String[] args) {
        launch();
    }


}