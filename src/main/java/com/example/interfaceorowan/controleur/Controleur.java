package com.example.interfaceorowan.controleur;

import com.example.interfaceorowan.vue.BasicVue;
import com.example.interfaceorowan.HelloApplication;
import com.example.interfaceorowan.modele.Modele;
import com.example.interfaceorowan.vue.LoginVue;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;


public class Controleur  implements PropertyChangeListener {

    private Stage stage;
    private Modele modele = Modele.getModeleinstance();

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

    private void basicVueDisplayer(){
        BasicVue bv = new BasicVue(stage);
        bv.addPropertyChangeListener(this);
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
            case "disconnection":
                disconnectionDisplayer();
        }
    }
}
