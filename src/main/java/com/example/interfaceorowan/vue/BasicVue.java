package com.example.interfaceorowan.vue;

import com.example.interfaceorowan.modele.Data;
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
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.io.File;
import java.util.ArrayList;

public class BasicVue {
    private final Scene scene;
    public PropertyChangeSupport support = new PropertyChangeSupport(this);

    public BasicVue(Stage stage) {
        stage.setTitle("Technical View");
        stage.setFullScreen(true);
        //stage.setResizable(true);

        BorderPane borderPane = new BorderPane();
        adjustPane(borderPane);

        drawGraph(borderPane);
        createVue(borderPane);
        this.scene = new Scene(borderPane, stage.getMaxHeight(), stage.getMaxWidth());
    }

    public void createVue(BorderPane borderPane){

        GridPane gridpane = new GridPane();
        Text userName = new Text(Modele.getModeleinstance().getUser().getName());
        Text role = new Text(Modele.getModeleinstance().getUser().getRole());
        /*Modele modele = Modele.getModeleinstance();
        Text userName = new Text(modele.getUser().getName());
        Text role = new Text(modele.getUser().getRole());
        */
        Button buttonAdmin = new Button("ADMIN");
        Button disconnectButton = new Button("se déconnecter");
        Image AMlogo = new Image(new File("").getAbsolutePath() + "\\src\\main\\resources\\images\\amlogo3.png");
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

        borderPane.setTop(new ImageView(AMlogo));
        borderPane.setAlignment(gridpane, Pos.TOP_LEFT);
        borderPane.setTop(gridpane);
        borderPane.setAlignment(gridpane, Pos.TOP_RIGHT);

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


    private void drawGraph(BorderPane borderPane) {
        Modele modele = Modele.getModeleinstance();
        ArrayList<Data> data = modele.getData();
        // Create the line chart with X and Y axis
        final NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("time");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Values");
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Time Series Graph");

        // Retrieve data from the model and load it into the chart
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        System.out.println(data);
        for (Data dataPoint : data) {
            // Add the Data object to the series
            series.getData().add(new XYChart.Data<>(dataPoint.getTime(), dataPoint.getValue()));
        }
        lineChart.getData().add(series);


        // Add the chart to the BasicVue
        borderPane.setCenter(lineChart);
        // Set the size of the chart
        //lineChart.setPrefSize(600, 200);
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
