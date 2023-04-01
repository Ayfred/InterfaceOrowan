package com.example.interfaceorowan.controleur;

/*
import org.h2.jdbcx.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;


public class DatabaseConnection {
    Connection dbConnection;
    static final String DB_URL = "jdbc:h2:~/test";
    static final String USER = "sa";
    static final String PASS = "";
    PreparedStatement insertion;
    public static DatabaseConnection manager = null;

    public DatabaseConnection() {
    }

    public static DatabaseConnection getInstance() {
        if (manager == null) {
            manager = new DatabaseConnection();
        }
        return manager;
    }

    //Méthode pour vérifier si une table SQL existe
    static boolean tableExistsSQL(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData md = connection.getMetaData();
        ResultSet tables = md.getTables(null, null, tableName, null);
        return tables.next();
    }

    private void openDBConnection() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(DB_URL);
        dataSource.setUser(USER);
        dataSource.setPassword(PASS);

        try {
            dbConnection=dataSource.getConnection();
            System.out.println("Connection réussie.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection échouée.");
            System.exit(0);
        }
    }
    public void CreateWorkerTable() throws SQLException {
        Statement stmt = dbConnection.createStatement();
        try {
            String query = "CREATE TABLE WORKER (id INTEGER not NULL,name VARCHAR(255),password VARCHAR(255))";
            stmt.executeUpdate(query);
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void PreparedStatementWorker() {
        try {
            insertion=dbConnection.prepareStatement("INSERT INTO WORKER(id, name,password) VALUES (?, ?, ?)");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private int CountWorker(){
        int nbWorker = 0;
        // Utilisation d'une clause try-ressource permettant de gérer les exceptions d'ouverture
        // et de fermeture (automatique) d'une ressource (interface Closeable)
        try (Statement st = dbConnection.createStatement()) {

            // Les requêtes de consultation sont éxécutées avec la méthode executeQuery.
            // Cette méthode retourne un objet ResultSet contenant le résultat.
            // Si cette requête est récurrente, il est possible d'utiliser un PreparedStatement.
            ResultSet rs = st.executeQuery("select COUNT(*) as nb from WORKER");
            while (rs.next())
            {
                // De manière alternative, les méthodes get d'un ResultSet peuvent utiliser le nom de la colonne
                // à la place de l'indice de la colonne sélectionnée dans la requête.
                // En SQL, les indices démarrent à 1 et non 0.
                nbWorker = rs.getInt("nb");
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return nbWorker;
    }

    public void InsertPerson(String name,String password) throws SQLException {
        // On vérifie que la table user existe
        if(tableExistsSQL(manager.dbConnection, "WORKER")){
            System.out.println("Table déjà existante");
        }
        else{
            //Create the table otherwise
            System.out.println("Table non existante");
            manager.CreateWorkerTable();
            System.out.println("La table WORKER est crée");
        }
        // Preparation du statement
        manager.PreparedStatementWorker();
        // Calcul de l'identifiant du nouveau worker
        int id = manager.CountWorker() + 1;


        // Insertion de la nouvelle personne
        try {
            // Fixe la valeur des paramètres de la requête avant exécution.
            // Les indices numériques sont numérotés à partir de 1 et non 0.
            insertion.setInt(1,id);
            insertion.setString(2,name);
            insertion.setString(3,password);
            //L'exécution des requêtes de modification est déclenchée par la méthode executeUpdate
            insertion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void displayPersons() {
        //Il faut tout display pour l'administrateur
        Collection<String> personNames = retrievePersonsName();

        for (String s : personNames) {
            System.out.println(s);
        }
    }

    private Collection<String> retrievePersonsName() {

        ArrayList<String> names = new ArrayList<>();



        // Utilisation d'une clause try-ressource permettant de gérer les exceptions d'ouverture
        // et de fermeture (automatique) d'une ressource (interface Closeable)
        try (Statement st = dbConnection.createStatement()) {

            // Les requêtes de consultation sont éxécutées avec la méthode executeQuery.
            // Cette méthode retourne un objet ResultSet contenant le résultat.
            // Si cette requête est récurrente, il est possible d'utiliser un PreparedStatement.
            ResultSet rs = st.executeQuery("select * from WORKER");

            //Itérateur. Retourne True quand il se positionne sur le tuple résultat suivant.
            while (rs.next())
            {
                // De manière alternative, les méthodes get d'un ResultSet peuvent utiliser le nom de la colonne
                // à la place de l'indice de la colonne sélectionnée dans la requête.
                // En SQL, les indices démarrent à 1 et non 0.
                names.add(rs.getString(2));
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }


    public static void main(String[] a) throws Exception {
        DatabaseConnection manager = DatabaseConnection.getInstance();
        manager.openDBConnection();
        manager.InsertPerson("toto6","testPassword6");
        manager.displayPersons();
        //Ce qui reste à faire : methode pour afficher password et id/ delete table/Person / getPassword via id/ name unique
    }
}
*/