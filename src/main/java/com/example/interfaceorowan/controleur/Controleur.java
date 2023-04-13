package com.example.interfaceorowan.controleur;

import com.example.interfaceorowan.vue.AdministratorVue;
import com.example.interfaceorowan.vue.BasicVue;
import com.example.interfaceorowan.HelloApplication;
import com.example.interfaceorowan.modele.Modele;
import com.example.interfaceorowan.vue.LoginVue;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class Controleur  implements PropertyChangeListener {

    private Stage stage;
    private Modele model;
    private Modele modele = Modele.getModeleinstance();

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
        BasicVue bv = new BasicVue(stage);
        bv.addPropertyChangeListener(this);
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
