package com.example.interfaceorowan.vue;

import com.example.interfaceorowan.HelloApplication;
import com.example.interfaceorowan.controleur.Controleur;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.image.Image ;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;

public class BasicVue {
    private Scene scene;
    private BorderPane borderPane;
    public PropertyChangeSupport support = new PropertyChangeSupport(this);


    public BasicVue(Stage stage) {
        stage.setTitle("Technical View");
        stage.setResizable(true);

        borderPane = new BorderPane();
        adjustPane(borderPane);
        createVue(borderPane);
        this.scene = new Scene(borderPane, stage.getMaxWidth(), stage.getMaxHeight());
    }

    public void createVue(BorderPane borderPane){

        HBox userinfos = new HBox(8);
        Label userName = new Label("");
        Label role = new Label();
        Image AMlogo = new Image(new File("").getAbsolutePath() + "\\src\\main\\resources\\images\\amlogo2.png");
        Image userLogo = new Image(new File("").getAbsolutePath() + "\\src\\main\\resources\\images\\userLogo.png");

        userinfos.getChildren().add(new ImageView(AMlogo));
        borderPane.setLeft(new ImageView(AMlogo));
        borderPane.setRight(new ImageView(userLogo));
    }

    public Scene getScene() {
        return scene;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);

    }

    private void adjustPane(BorderPane borderPane){
        /*borderPane.setAlignment(Pos.CENTER);
        borderPane.setHgap(10);
        borderPane.setVgap(10);*/
        borderPane.setPadding(new Insets(25, 25, 25, 25));
    }
}
