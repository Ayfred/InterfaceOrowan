package com.example.interfaceorowan.modele;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;
import java.util.ArrayList;

public class Modele {
    private final DatabaseConnection database;
    private User user = null;
    private static Modele modeleInstance;
    private int index = 1;


    public PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Singleton containing the user, to obtain easily his privilege
     */
    private Modele() {

        database = DatabaseConnection.getInstance();
        this.database.loadDataFromDatabase(index);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        this.database.loadDataFromDatabase(index);
    }

    /**
     * to get the instance from the singleton
     */
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
     * @return the list of people's names and roles
     */
    public ArrayList<String>[] getUsers(){
        return database.retrievePersonsNameandRole();
    }

    /**
     * Delete a user using his name
     * @param name returns the name of the user
     * @throws SQLException if there is an error in sql queries
     */
    public void deleteUser(String name) throws SQLException {

        database.deleteWorker(name);
        support.firePropertyChange("UserSupprimé",null,null);
    }

    /**
     * fetch data from datatbase
     * @return the list of the data
     */
    public ArrayList<Data> getData(){

        return (ArrayList<Data>) database.getData();
    }

    public String getColumnName(){
        return database.getColumnName();
    }

    public User getUser() {
        return user;
    }

}
