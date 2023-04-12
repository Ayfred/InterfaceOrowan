package com.example.interfaceorowan;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Orowan");

        primaryStage.setResizable(false);
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 1500, 1000);

        adjustGrid(grid);
        createInterface(grid);

        primaryStage.setScene(scene);
        scene.getStylesheets().add
                (HelloApplication.class.getResource("design.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void createInterface(GridPane gp){

        final Text actiontarget = new Text();
        Text scenetitle = new Text("Login");
        Label userName = new Label("User Name:");
        TextField userTextField = new TextField();
        Label pw = new Label("Password:");
        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        PasswordField pwBox = new PasswordField();
        Rectangle r1 =new Rectangle();
        StackPane sp = new StackPane();
        GridPane grid = new GridPane();

        r1.setWidth(310);
        r1.setHeight(145);
        r1.setFill(Color.WHITE);
        r1.opacityProperty().set(0.5);

        //grid.add(scenetitle, 0, 0, 2, 1);
        grid.add(userName, 0, 1);
        grid.add(userTextField, 1, 1);
        grid.add(pw, 0, 2);
        grid.add(pwBox, 1, 2);

        StackPane.setMargin(grid, new Insets(20, 20, 20, 20));
        sp.setAlignment(grid,Pos.CENTER);

        sp.getChildren().addAll(r1, grid);
        gp.add(sp,0, 0);

        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        grid.add(actiontarget, 1, 6);

        scenetitle.setId("welcome-text");
        actiontarget.setId("actiontarget");

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                String identifiant = userTextField.getText();
                String mdp = pwBox.getText();
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Identifiant ou mot de passe incorrect");
            }
        });
    }

    private void adjustGrid(GridPane grid){
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

    }
}