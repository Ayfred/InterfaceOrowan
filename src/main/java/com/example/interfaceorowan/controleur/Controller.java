package com.example.interfaceorowan.controleur;

import com.example.interfaceorowan.modele.Data;
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
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.Objects;


public class Controller implements PropertyChangeListener {
    private final Stage stage;
    private final Modele modele;

    /**
     * Listen to event, change view, create a dynamic dipslay
     * @param stage  a Stage object representing the main window of the application
     */
    public Controller(Stage stage) {

        this.stage = stage;
        this.modele = Modele.getModeleinstance();
        modele.addPropertyChangeListener(this);
        loginVueDisplayer();
    }

    /**
     * Create and displays a login Vue
     */
    private void loginVueDisplayer(){

        LoginVue lv = new LoginVue();
        lv.addPropertyChangeListener(this);
        Scene scene = lv.getScene();

        stage.setScene(scene);
        scene.getStylesheets().add(HelloApplication.class.getResource("design.css").toExternalForm());
        stage.show();
    }

    /**
     * Create and displays an administrator view
     */
    private void administratorVueDisplayer(){

        AdministratorVue av = new AdministratorVue();
        av.addPropertyChangeListener(this);
        Scene scene = av.getScene();

        stage.setScene(scene);
        scene.getStylesheets().add(HelloApplication.class.getResource("AdministratorDesign.css").toExternalForm());
        stage.show();
    }

    /**
     * Create and displays a basic view
     */
    private void basicVueDisplayer(){

        BasicVue bv = new BasicVue(stage);
        bv.addPropertyChangeListener(this);

        Scene scene = bv.getScene();
        stage.setScene(scene);
        scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("interfaceDesign.css")).toExternalForm());
        stage.show();
    }

    /**
     * Displays confirmation window for deconnection
     */
    private void disconnectionDisplayer(){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Souhaitez vous vous déconnecter ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            loginVueDisplayer();
        }
    }

    /**
     * Call modele login method to check id and password
     */
    private void checkId(String name, String pw){

        try {
            modele.login(name, pw);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * EventListener : analyse event and act considering it
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch(evt.getPropertyName()){// /!\Ne pas changer le switch en enhanced switch car non compatible avec Java 8
            case "connexion":
                checkId((String) evt.getOldValue(), (String) evt.getNewValue());
                break;
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
                break;
            case "ChangeData":
                changeData();
                break;
        }
    }

    /**
     * CHanges the data
     */
    private void changeData() {
        int ind = modele.getIndex() + 1;
        if(ind >21) ind = 1;
        System.out.println(ind);
        modele.setIndex(ind);

    }
}
