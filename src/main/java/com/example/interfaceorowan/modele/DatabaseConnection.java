package com.example.interfaceorowan.modele;

import org.h2.jdbcx.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DatabaseConnection {
    Connection dbConnection;
    static final String DB_URL = "jdbc:h2:~/test";
    static final String USER = "maxime";
    static final String PASS = "";
    private PreparedStatement insertion;
    public static DatabaseConnection manager = null;
    private final List<Data> data;
    private String columnName;

    /**
     * enable connection with H2 database
     */
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

    /**
     * Singleton method
     * @return  instance from the singleton
     */
    public static DatabaseConnection getInstance() {

        if (manager == null) {
            manager = new DatabaseConnection();
        }
        return manager;
    }

    /**
     * Chek wether a table exists in the database
     */
    static boolean tableExistsSQL(Connection connection, String tableName) throws SQLException {

        DatabaseMetaData md = connection.getMetaData();
        ResultSet tables = md.getTables(null, null, tableName, null);
        return tables.next();
    }

    /**
     * create connection with database
     */
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


    /**
     * Create table Worker in dataabse
     * @throws SQLException if there is an error in sql queries
     */
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

    /**
     * Create an instance of Worker tablle in database
     */
    public void preparedStatementWorker() {

        try {
            insertion=dbConnection.prepareStatement("INSERT INTO WORKER(id,name,password,role) VALUES (?,?,?,?)");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Return password from considered user
     */
    public void preparedStatementgetPassword() {

        try {
            insertion=dbConnection.prepareStatement("SELECT password FROM WORKER WHERE name=?");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Return role from considered user
     */
    public void preparedStatementgetRole() {

        try {
            insertion=dbConnection.prepareStatement("SELECT role FROM WORKER WHERE name=?");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Set password from considered user
     */
    public void preparedStatementsetPassword() {

        try {
            insertion=dbConnection.prepareStatement("UPDATE WORKER SET password=? WHERE name=?");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Set role from considered user
     */
    public void preparedStatementsetRole() {

        try {
            insertion=dbConnection.prepareStatement("UPDATE WORKER SET role=? WHERE name=?");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Delete the user
     */
    public void preparedStatementDeleteUser() {

        try {
            insertion=dbConnection.prepareStatement("DELETE FROM WORKER WHERE name=?");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Count how much worker are stored in the database
     * @return the number of workers
     */
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

    /**
     * Insert a nw person in the database
     * @param name name of the account
     * @param password password of the account
     * @return returns a boolean that shows the method has been correctly executed
     * @throws SQLException if there is an error in sql queries
     */
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

    /**
     * Displays all persons
     */
    public void displayPersons() {

        //Il faut tout display pour l'administrateur
        Collection<String> personNames = retrievePersonsName();
        for (String s : personNames) {
            System.out.println(s);
        }
    }

    /**
     * return name from all person in database
     * @return the names of people in the database
     */
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

    /**
     * Return names and roles from all persons in database
     * @return the arraylist of the people's names and roles
     */
    public ArrayList[] retrievePersonsNameandRole() {

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
        ArrayList[] result = new ArrayList[2];
        result[0] = names;
        result[1] = roles;
        return result;
    }

    /**
     * Delete a worker
     * @param name name of the worker to delete
     * @throws SQLException if there is an error in sql queries
     */
    public void deleteWorker(String name) throws SQLException {
         manager.preparedStatementDeleteUser();
         insertion.setString(1,name);
         insertion.execute();
    }

    /**
     * Retrieve the password
     * @param name name of the user
     * @return returns the passsword
     * @throws SQLException if there is an error in sql queries
     */
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

    /**
     * Get the role of a user
     * @param name name of the user
     * @return returns the name
     * @throws SQLException if there is an error in sql queries
     */
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

    /**
     * Changes the role of a user
     * @param name name of the user
     * @param role role that going to be replaced with
     */
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

    /**
     * Collect data from database
     * @param index number of the data to show
     */
    public void loadDataFromDatabase(int index) {
        int i = 0;
        data.clear();
        //String tableName = "INITIAL_VALUES";
        String tableName = "MEAN_OROWAN";
        try {

            // Execute a SELECT statement to retrieve the data
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);

            // Get the metadata for the result set
            ResultSetMetaData metaData = rs.getMetaData();

            // Get the column name for the given index
            columnName = metaData.getColumnName(index);

            // Loop through the result set and add each value to the data list
            while (rs.next()) {
                // Attention ne pas enlever les if !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                if(index != 2 && index != 21 && tableName.equals("OROWAN_OUTPUT")) {
                    data.add(new Data(i, rs.getDouble(index)));

                }else if(tableName.equals("INITIAL_VALUES")){
                    data.add(new Data(i, Double.parseDouble(rs.getString(index).replace(",", "."))));
                }else if(tableName.equals("MEAN_OROWAN")){
                    data.add(new Data(i, rs.getDouble(index)));
                }
                i++;
            }

        } catch (SQLException e) {
            e.printStackTrace();    
        }
    }

    /**
     * get Data arraylist
     * @return the arraylist of data from database
     */
    public List<Data> getData() {
        return data;
    }

    /**
     * get the column names from the database
     * @return returns a name
     */
    public String getColumnName() {
        return columnName;
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
        manager.changeRole("test2","ADMIN");
        manager.changePassword("toto6","toto6");
        manager.getPassword("toto6");
        manager.getRole("toto6");
    }
}
