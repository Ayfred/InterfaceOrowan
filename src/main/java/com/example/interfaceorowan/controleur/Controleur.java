package com.example.interfaceorowan.controleur;

import com.example.interfaceorowan.vue.AdministratorVue;
import com.example.interfaceorowan.modele.DatabaseConnection;
import com.example.interfaceorowan.vue.BasicVue;
import com.example.interfaceorowan.HelloApplication;
import com.example.interfaceorowan.modele.Modele;
import com.example.interfaceorowan.vue.LoginVue;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class Controleur  implements PropertyChangeListener {

    private Stage stage;
    private Modele model;
    private Modele modele = Modele.getModeleinstance();
    private DatabaseConnection modele = null;


    public Controleur(Stage stage) {
        this.stage = stage;
        this.model = Modele.getModeleinstance();
        model.addPropertyChangeListener(this);
        loginVueDisplayer();

    }

    private void loginVueDisplayer(){
        LoginVue lv = new LoginVue();
        lv.addPropertyChangeListener(this);
        Scene scene = lv.getScene();

        stage.setScene(scene);
        scene.getStylesheets().add(HelloApplication.class.getResource("design.css").toExternalForm());
        stage.show();
    }
    private void administratorVueDisplayer(){
        AdministratorVue av = new AdministratorVue();
        av.addPropertyChangeListener(this);
        Scene scene = av.getScene();

        stage.setScene(scene);
        scene.getStylesheets().add(HelloApplication.class.getResource("AdministratorDesign.css").toExternalForm());
        stage.show();
    }

    private void basicVueDisplayer(){
        modele = DatabaseConnection.getInstance();
        modele.loadDataFromDatabase();
        BasicVue bv = new BasicVue(stage);
        bv.addPropertyChangeListener(this);


        // Create the line chart
        LineChart<Number, Number> lineChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        lineChart.setTitle("Data Chart");
        System.out.println(modele.getData());

        // Retrieve data from the model and load it into the chart
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < modele.getData().size(); i++) {
            series.getData().add(new XYChart.Data<>(i + 1, modele.getData().get(i)));
        }
        lineChart.getData().add(series);

        // Add the chart to the BasicVue
        bv.setChart(lineChart);

        Scene scene = bv.getScene();

        stage.setScene(scene);
        scene.getStylesheets().add(HelloApplication.class.getResource("interfaceDesign.css").toExternalForm());
        stage.show();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch(evt.getPropertyName()){
            case "IdentifiacationReussie":
                basicVueDisplayer();
                break;
            case "RoleModifié":
                administratorVueDisplayer();
                break;
            case"UserSupprimé":
                administratorVueDisplayer();
                break;
            case"AdminVue":
                administratorVueDisplayer();
                break;
        }
    }
}
