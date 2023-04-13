package com.example.interfaceorowan.vue;

import com.example.interfaceorowan.modele.Modele;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import com.example.interfaceorowan.HelloApplication;
import com.example.interfaceorowan.controleur.Controleur;
import com.example.interfaceorowan.modele.Modele;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image ;
import javafx.scene.control.Button;
import javafx.scene.control.Button;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.io.File;
import java.util.ArrayList;

public class BasicVue {
    private Scene scene;
    private BorderPane borderPane;
    public PropertyChangeSupport support = new PropertyChangeSupport(this);


    public BasicVue(Stage stage) {
        stage.setTitle("Technical View");
        stage.setFullScreen(true);

        borderPane = new BorderPane();
        adjustPane(borderPane);
        createVue(borderPane);
        this.scene = new Scene(borderPane, stage.getMaxHeight(), stage.getMaxWidth());
    }

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
        buttonAdmin.setVisible(Modele.getModeleinstance().getUser().getRole().equals("ADMIN"));

        gridpane.setHalignment(userLogoView, HPos.CENTER);
        gridpane.setHalignment(disconnectButton, HPos.CENTER);
        gridpane.setHgap(20);
        gridpane.setVgap(5);

        borderPane.setLeft(new ImageView(AMlogo));
        borderPane.setRight(gridpane);

        //Partie du code pour afficher le graphique
        // Définir les axes du graphique
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Temps");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Valeur");
        // Créer un graphique de courbe
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Evolution des ventes");

        // Ajouter des données au graphique
        XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Ventes");

        ArrayList<Double> data  = Modele.getModeleinstance().getData();
        for (int i=0; i<data.size();i++){
            dataSeries.getData().add(new XYChart.Data<>(0.2*i, data.get(i)));
        }


        // Ajouter les données au graphique
        lineChart.getData().add(dataSeries);

        borderPane.setCenter(lineChart);


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

    public void setChart(LineChart<Number, Number> chart) {
        borderPane.getChildren().add(chart);
    }
}
