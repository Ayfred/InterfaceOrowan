package com.example.interfaceorowan.modele;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class Modele {
    private final DatabaseConnection database;
    private User user = null;
    private static Modele modeleInstance;


    public PropertyChangeSupport support = new PropertyChangeSupport(this);

    private Modele() {
        /**
         * Singleton containing the user, to obtain easily his privilege
         */
        database = DatabaseConnection.getInstance();
        database.loadDataFromDatabase();
    }

    public static Modele getModeleinstance(){
        /**
         * to get the instance from the singleton
         */
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
    /**
     * Methode qui permet à l'utilisateur de creer un compte avec un mdp et un username
     * le resultat est envoyé avec le firePropertyChange
     * @param name On entre son username
     * @param password On rentre son mdp
     */
    public void createAccount(String name,String password) throws SQLException{
        if(database.InsertPerson(name, password)){
            support.firePropertyChange("CompteCrée",null,null);
        }
        else{
            support.firePropertyChange("IdentifiantDejaUtilisé",null,null);
        }
    }
    /**
     * Methode qui permet de changer le role d'une personne en donnant son nom et son nouveau role
     * le resultat est envoyé avec le firePropertyChange
     * @param name On entre son username
     * @param role On rentre son nouveau role
     */
    public void changeRole(String name,String role){
        database.changeRole(name,role);
        support.firePropertyChange("RoleModifié",null,null);
    }
    /**
     * Methode qui permet de recuperer tout les users ainsi que leur roles
     * le resultat est envoyé avec le firePropertyChange
     */
    public ArrayList<String>[] getUsers(){
        return database.retrievePersonsNameandRole();
    }
    public void deleteUser(String name) throws SQLException {
        database.deleteWorker(name);
        support.firePropertyChange("UserSupprimé",null,null);
    }

    public ArrayList<Data> getData(){
        return (ArrayList<Data>) database.getData();
    }

    public User getUser() {
        return user;
    }

}
