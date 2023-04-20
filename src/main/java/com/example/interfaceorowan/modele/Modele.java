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
     * This is a method that allows the user to log in by verifying their username and password.
     * The result is sent with the firePropertyChange.
     * @param name: The user's username.
     * @param password: The user's password.
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
     * Method that allows the user to create an account with a username and password.
     * The result is sent using the firePropertyChange method.
     * @param name The username to be used for the new account.
     * @param password The password to be used for the new account.
     **/
    public void createAccount(String name,String password) throws SQLException{
        if(database.InsertPerson(name, password)){
            support.firePropertyChange("CompteCrée",null,null);
        }
        else{
            support.firePropertyChange("IdentifiantDejaUtilisé",null,null);
        }
    }

    /**
     * Method that allows changing the role of a person by giving their name and their new role.
     * The result is sent using the firePropertyChange method.
     * @param name The username of the person.
     * @param role The new role to be assigned.
     */
    public void changeRole(String name,String role){
        database.changeRole(name,role);
        support.firePropertyChange("RoleModifié",null,null);
    }

    /**
     * Method that retrieves all users and their roles
     * The result is sent with the firePropertyChange method
     * @return the array of people's names and roles
     **/
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

    /**
     * get column name
     * @return string of the name
     */
    public String getColumnName(){
        return database.getColumnName();
    }

    /**
     * get the user
     * @return returns the user
     */
    public User getUser() {
        return user;
    }

}
