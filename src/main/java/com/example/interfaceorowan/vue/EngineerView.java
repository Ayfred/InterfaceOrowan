package com.example.interfaceorowan.vue;

import com.example.interfaceorowan.modele.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;

public class EngineerView extends BasicView {
    public EngineerView(Stage stage) {
        super(stage);
    }

    /**
     * Create buttons and labels
     * @param borderPane
     */
    public void createVue(BorderPane borderPane){

        GridPane gridpane = new GridPane();
        Text userName = new Text("GEGE");
        Text role = new Text("worker");
        Button buttonAdmin = new Button("ADMIN");
        Button disconnectButton = new Button("se déconnecter");
        Image AMlogo = new Image(new File("").getAbsolutePath() + "\\src\\main\\resources\\images\\amlogo2.png");
        Image userLogo = new Image(new File("").getAbsolutePath() + "\\src\\main\\resources\\images\\userLogo2.png");
        ImageView userLogoView = new ImageView(userLogo);

        role.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 28));
        userName.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 28));
        disconnectButton.setFont(Font.font("verdana", FontWeight.LIGHT, FontPosture.REGULAR, 10));

        gridpane.add(userName,0, 0);
        gridpane.add(userLogoView, 1, 0);
        gridpane.add(role , 0, 1);
        gridpane.add(disconnectButton, 1, 1);
        gridpane.getChildren().add(buttonAdmin);
        buttonAdmin.setVisible(Model.getModeleinstance().getUser().getRole().equals("ADMIN"));

        gridpane.setHalignment(userLogoView, HPos.CENTER);
        gridpane.setHalignment(disconnectButton, HPos.CENTER);
        gridpane.setHgap(20);
        gridpane.setVgap(5);

        borderPane.setLeft(new ImageView(AMlogo));
        borderPane.setRight(gridpane);

        disconnectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                support.firePropertyChange("disconnection", null, null);
            }
        });

        buttonAdmin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                support.firePropertyChange("AdminVue",null,null);
            }
        });
    }
}
