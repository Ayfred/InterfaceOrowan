package com.example.interfaceorowan.vue;

import com.example.interfaceorowan.modele.Modele;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;

//A login view is a Scene for the login part, ie the first view to be displayed, called by HelloApplication
public class LoginVue {
    private GridPane grid;
    private Scene scene;
    public PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public LoginVue() {
        this.grid = new GridPane();
        this.scene = new Scene(grid, 1000, 500);

        adjustGrid(grid);
        createInterface(grid);
    }

    public GridPane getGrid() {
        return grid;
    }

    public void setGrid(GridPane grid) {
        this.grid = grid;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    private void createInterface(GridPane gp){

        final Text actiontarget = new Text();
        Text scenetitle = new Text("Login");
        Label userName = new Label("User Name:");
        TextField userTextField = new TextField();
        userTextField.setText("");
        Label pw = new Label("Password:");
        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        PasswordField pwBox = new PasswordField();
        pwBox.setText("");
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
        StackPane.setAlignment(grid, Pos.CENTER);

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
                support.firePropertyChange("connexion",userTextField.getText(),  pwBox.getText());
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
