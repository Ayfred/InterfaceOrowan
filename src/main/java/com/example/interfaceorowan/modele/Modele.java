package com.example.interfaceorowan.modele;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;

public class Modele {
    private DatabaseConnection database = DatabaseConnection.getInstance();
    private User user = null;
    private static Modele modeleInstance;

    public PropertyChangeSupport support = new PropertyChangeSupport(this);

    private Modele() {
    }

    public static Modele getModeleinstance(){
        if(modeleInstance == null){
            modeleInstance = new Modele();
        }
        return modeleInstance;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Methode qui permet à l'utilisateur de se connecter et verifie le mdp et le username
     * le resultat est envoyé avec le firePropertyChange
     * @param name On entre son username
     * @param password On rentre son mdp
     */
    public void login(String name,String password) throws SQLException {
        String databasePassword = database.getPassword(name);
        if(databasePassword != null && password.equals(databasePassword)){
            // on envoie on controleur l'information que la connection est réussi ainsi que le role de l'utilisateur
            String role = database.getRole(name);
            //creation de l'instance singleton user

            user = User.getInstance(name,role);
            support.firePropertyChange("IdentifiacationReussie",null,role);

        }
        else{
            support.firePropertyChange("IdentificationEchoué",null,null);
        }
    }

    public static void main(String[] a) throws Exception {
        Modele modele = new Modele();
        modele.login("toto6","testNewPassword");
        System.out.println(modele.user.getName());
    }

    public User getUser() {
        return user;
    }
}
