package com.example.interfaceorowan.vue;

import com.example.interfaceorowan.modele.Modele;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
import java.util.ArrayList;

public class IngeniorVue extends BasicVue{
    public IngeniorVue(Stage stage) {
        super(stage);
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
}
