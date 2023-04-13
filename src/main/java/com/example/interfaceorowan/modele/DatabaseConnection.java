package com.example.interfaceorowan.modele;


import org.h2.jdbcx.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class DatabaseConnection {
    Connection dbConnection;
    static final String DB_URL = "jdbc:h2:~/test";
    static final String USER = "sa";
    static final String PASS = "";
    PreparedStatement insertion;
    public static DatabaseConnection manager = null;
    private final List<Double> data;

    public DatabaseConnection() {
            data = new ArrayList<>();
            JdbcDataSource dataSource = new JdbcDataSource();
            dataSource.setURL(DB_URL);
            dataSource.setUser(USER);
            dataSource.setPassword(PASS);

            try {
                dbConnection=dataSource.getConnection();
                System.out.println("Connection réussie.");
            }
            catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Connection échouée.");
                System.exit(0);
            }
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
            String query = "CREATE TABLE WORKER (id INTEGER not NULL,name VARCHAR(255),password VARCHAR(255),role VARCHAR(255))";
            stmt.executeUpdate(query);
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void preparedStatementWorker() {
        try {
            insertion=dbConnection.prepareStatement("INSERT INTO WORKER(id,name,password,role) VALUES (?,?,?,?)");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    public void preparedStatementgetPassword() {
        try {
            insertion=dbConnection.prepareStatement("SELECT password FROM WORKER WHERE name=?");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    public void preparedStatementgetRole() {
        try {
            insertion=dbConnection.prepareStatement("SELECT role FROM WORKER WHERE name=?");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void preparedStatementsetPassword() {
        try {
            insertion=dbConnection.prepareStatement("UPDATE WORKER SET password=? WHERE name=?");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    public void preparedStatementsetRole() {
        try {
            insertion=dbConnection.prepareStatement("UPDATE WORKER SET role=? WHERE name=?");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    public void preparedStatementDeleteUser() {
        try {
            insertion=dbConnection.prepareStatement("DELETE FROM WORKER WHERE name=?");
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

    public boolean InsertPerson(String name,String password) throws SQLException {
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

        // Calcul de l'identifiant du nouveau worker
        int id = manager.CountWorker() + 1;

        // Verification pour voir si l'identifiant est deja utilisé
        ArrayList<String> names;
        names = (ArrayList<String>) retrievePersonsName();
        if (names.contains(name)){
            return false;
        }
        else {
            manager.preparedStatementWorker();
            // Insertion de la nouvelle personne
            try {
                // Fixe la valeur des paramètres de la requête avant exécution.
                // Les indices numériques sont numérotés à partir de 1 et non 0.
                insertion.setInt(1, id);
                insertion.setString(2, name);
                insertion.setString(3, password);
                insertion.setString(4,"OPERATOR");
                //L'exécution des requêtes de modification est déclenchée par la méthode executeUpdate
                insertion.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
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
    public ArrayList<String>[] retrievePersonsNameandRole() {

        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> roles = new ArrayList<>();

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
                roles.add(rs.getString(4));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<String>[] result = new ArrayList[2];
        result[0] = names;
        result[1] = roles;
        return result;
    }

    public void deleteWorker(String name) throws SQLException {
         manager.preparedStatementDeleteUser();
         insertion.setString(1,name);
         insertion.execute();
    }

    public String getPassword(String name) throws SQLException {
        String password = "";
        // test pour verifier que le name est bien dans la db
        ArrayList<String> names;
        names = (ArrayList<String>) retrievePersonsName();
        if (names.contains(name)){
            manager.preparedStatementgetPassword();
                insertion.setString(1, name);
                ResultSet rs = insertion.executeQuery();
                if (rs.next())
                {
                    password = rs.getString("password");
                }
                else{
                    System.out.println("mdp non trouvé");
                }
        }
        else{
            System.out.println("identifiant inconnus");
        }
        return password;
    }

    public String getRole(String name) throws SQLException {
        String role = null;
        // test pour verifier que le name est bien dans la db
        ArrayList<String> names;
        names = (ArrayList<String>) retrievePersonsName();
        if (names.contains(name)){
            manager.preparedStatementgetRole();
            insertion.setString(1, name);
            ResultSet rs = insertion.executeQuery();
            if (rs.next())
            {
                role = rs.getString("role");
            }
            else{
                System.out.println("role non trouvé");
            }
        }

        else{
            System.out.println("identifiant inconnus");
        }
        return role;
    }

    public void changeRole (String name,String role){
        ArrayList<String> names;
        names = (ArrayList<String>) retrievePersonsName();
        if (names.contains(name)) {
            manager.preparedStatementsetRole();
            try {
                insertion.setString(1, role);
                insertion.setString(2,name);
                insertion.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void changePassword (String name,String password){
        ArrayList<String> names;
        names = (ArrayList<String>) retrievePersonsName();
        if (names.contains(name)) {
            manager.preparedStatementsetPassword();
            try {
                insertion.setString(1, password);
                insertion.setString(2,name);
                insertion.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ArrayList<Double> loadDataFromDatabase() {

        try {

            // Execute a SELECT statement to retrieve the data
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM OROWAN_OUTPUT");

            // Loop through the result set and add each value to the data list
            while (rs.next()) {
                System.out.printf("data  = %s%n", rs.getDouble("SIGMA_MOY"));
                data.add(rs.getDouble(6));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (ArrayList<Double>) data;
    }

    public List<Double> getData() {
        return data;
    }

    public static void main(String[] a) throws Exception {
        DatabaseConnection manager = DatabaseConnection.getInstance();
        manager.InsertPerson("test1","testPassword6");
        manager.InsertPerson("test2","2");
        manager.InsertPerson("test3","3");
        manager.InsertPerson("test4","4");
        manager.displayPersons();
        manager.getPassword("toto6");
        manager.getRole("toto6");
        manager.changeRole("toto6","ADMIN");
        manager.changePassword("toto6","toto6");
        manager.getPassword("toto6");
        manager.getRole("toto6");
    }
}
