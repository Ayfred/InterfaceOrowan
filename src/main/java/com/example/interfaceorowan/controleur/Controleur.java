package com.example.interfaceorowan.controleur;

import com.example.interfaceorowan.vue.AdministratorVue;
import com.example.interfaceorowan.modele.DatabaseConnection;
import com.example.interfaceorowan.vue.BasicVue;
import com.example.interfaceorowan.HelloApplication;
import com.example.interfaceorowan.modele.Modele;
import com.example.interfaceorowan.vue.LoginVue;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.Objects;


public class Controleur  implements PropertyChangeListener {

    private Stage stage;
    private Modele modele = Modele.getModeleinstance();
    private DatabaseConnection dbmodele = null;


    public Controleur(Stage stage) {
        this.stage = stage;
        this.modele = Modele.getModeleinstance();
        modele.addPropertyChangeListener(this);

        //loginVueDisplayer();
        basicVueDisplayer();

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
        dbmodele = DatabaseConnection.getInstance();
        dbmodele.loadDataFromDatabase();
        BasicVue bv = new BasicVue(stage);
        bv.addPropertyChangeListener(this);


        // Create the line chart
        LineChart<Number, Number> lineChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        lineChart.setTitle("Data Chart");
        System.out.println(dbmodele.getData());

        // Retrieve data from the model and load it into the chart
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < dbmodele.getData().size(); i++) {
            series.getData().add(new XYChart.Data<>(i + 1, dbmodele.getData().get(i)));
        }
        lineChart.getData().add(series);

        // Add the chart to the BasicVue
        bv.setChart(lineChart);

        Scene scene = bv.getScene();

        stage.setScene(scene);
        scene.getStylesheets().add(HelloApplication.class.getResource("interfaceDesign.css").toExternalForm());
        stage.show();
    }

    private void disconnectionDisplayer(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Souhaitez vous vous déconnecter ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            loginVueDisplayer();
        }
        /*DisconnectionView dv = new DisconnectionView();
        dv.addPropertyChangeListener(this);
        Scene scene = dv.getScene();

        stage.setScene(scene);
        //scene.getStylesheets().add(HelloApplication.class.getResource("interfaceDesign.css").toExternalForm());
        stage.show();*/
    }

    private void checkId(String name, String pw){
        try {
            modele.login(name, pw);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch(evt.getPropertyName()){
            case "connexion":
                checkId((String) evt.getOldValue(), (String) evt.getNewValue());
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
            case "disconnection":
                disconnectionDisplayer();
        }
    }
}
