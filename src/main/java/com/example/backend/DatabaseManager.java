package com.example.backend;

import org.h2.jdbcx.JdbcDataSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;


public class DatabaseManager {
    private static Connection dbConnection;
    private static DatabaseManager manager = null;
    public DatabaseManager(){
    }


    public static DatabaseManager getInstance() {
        if (manager == null) {
            manager = new DatabaseManager();
        }
        return manager;
    }

    public Connection getDbConnection() {
        return dbConnection;
    }

    // Etablit la connexion avec la base de données.
    // L'ouverture d'une connection est une opération lente et coûteuse en resssouces.
    // Une seule connection est suffisante pour gérer les requêtes dans un contexte non-concurrent
    // (parallélisation de requêtes).
    public void openDBConnection()
    {
        JdbcDataSource dataSource = new JdbcDataSource();
        String DB_URL = "jdbc:h2:~/test";
        dataSource.setURL(DB_URL);
        String USER = "maxime";
        dataSource.setUser(USER);
        String PASS = "";
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
     * Method to check whether a table exist in the database.
     * @param connection connection to the database
     * @param tableName name of the table
     * @return true if the table exists otherwise return false
     * @throws SQLException for the getMetaData() method
     */
    static boolean checkTable(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData md = connection.getMetaData();
        ResultSet tables = md.getTables(null, null, tableName, null);
        return tables.next();
    }


    public void add(File file, String name) throws IOException, SQLException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        Statement stmt = dbConnection.createStatement();

        String createTableQuery = "CREATE TABLE IF NOT EXISTS "+ name +" (id INT PRIMARY KEY, name VARCHAR(255), age INT)";
        stmt.executeUpdate(createTableQuery);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] data = line.split(",");
            String insertQuery = "INSERT INTO "+ name +" (id, name, age) VALUES (" + data[0] + ", '" + data[1] + "', " + data[2] + ")";
            stmt.executeUpdate(insertQuery);
        }
        bufferedReader.close();
    }


    //Add initial file
    public static void addInitialTable() throws SQLException {
        Statement stmt = dbConnection.createStatement();
        try {
            String createTableQuery =
                    "CREATE TABLE INITIAL_VALUES (" +
                            "  Lp VARCHAR(255)," +
                            "  MatID VARCHAR(255)," +
                            "  xTime VARCHAR(255)," +
                            "  xLoc VARCHAR(255)," +
                            "  EnThick VARCHAR(255), " +
                            "  ExThick VARCHAR(255)," +
                            "  EnTens VARCHAR(255)," +
                            "  ExTens VARCHAR(255)," +
                            "  RollForce VARCHAR(255)," +
                            "  FSlip VARCHAR(255)," +
                            "  Daiameter VARCHAR(255)," +
                            "  Rolled_length_for_Work_Rolls VARCHAR(255)," +
                            "  youngModulus VARCHAR(255)," +
                            "  Backup_roll_dia VARCHAR(255)," +
                            "  Rolled_length_for_Backup_rolls VARCHAR(255)," +
                            "  mu VARCHAR(255)," +
                            "  torque VARCHAR(255)," +
                            "  averageSigma VARCHAR(255)," +
                            "  inputError VARCHAR(255)," +
                            "  LubWFlUp VARCHAR(255)," +
                            "  LubWFlLo VARCHAR(255)," +
                            "  LubOilFlUp VARCHAR(255)," +
                            "  LubOilFlLo VARCHAR(255)," +
                            "  Work_roll_speed VARCHAR(255)" +
                            ")";
            stmt.executeUpdate(createTableQuery);
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    //Add MEAN Data
    static void addInitialData() throws SQLException, IOException {
        String csvFile = new File("").getAbsolutePath() + "/src/main/resources/com.example.backend/1939351_F2.csv";
        String tableName = "INITIAL_VALUES";
        String sql = "INSERT INTO INITIAL_VALUES (Lp, MatID, xTime, xLoc, EnThick, ExThick, EnTens, ExTens, RollForce, " +
                " FSlip, Daiameter, Rolled_length_for_Work_Rolls, youngModulus, Backup_roll_dia, Rolled_length_for_Backup_rolls" +
                ", mu, torque,  averageSigma, inputError, LubWFlUp, LubWFlLo, LubOilFlUp, LubOilFlLo, Work_roll_speed ) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?, ?, ? );";

        PreparedStatement pstmt = dbConnection.prepareStatement(sql);

        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        String line;

        while ((line = br.readLine()) != null) {
            String[] values = line.split("/");
            for (int i = 0; i < values.length-1; i++) {

                pstmt.setString(i+1, values[i]);
            }
            pstmt.executeUpdate();
        }

        br.close();
        pstmt.close();
    }



    public static void addOutputOrowanData() throws SQLException, IOException {

        String csvFile = new File("").getAbsolutePath() + "/src/main/resources/com.example.backend/outputOrowan.csv";
        String tableName = "OROWAN_OUTPUT";
        String sql = "INSERT INTO OROWAN_OUTPUT (\"CASE\", Errors, Rolling_Force, Forward_Slip, Rolling_Torque, Sigma_Moy," +
                " Sigma_Ini, Sigma_Out, Sigma_Max, Neutral_X, U_Forward_Slip, Contact_Length, Neutral_H, angle_Tresca_Bw," +
                " Neutral_Angle, angle_Tresca_Fw, U_Rolling_Force, Tresca_Presence, Total_Tresca, Alpha_Max, Has_Converged) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement pstmt = dbConnection.prepareStatement(sql);

        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        String line;
        br.readLine();
        while ((line = br.readLine()) != null) {
            String[] values = line.split("\t");
            for (int i = 0; i < values.length; i++) {
                pstmt.setString(i+1, values[i]);
            }
            pstmt.executeUpdate();
        }

        br.close();
        pstmt.close();
    }

    public static void addOutputOrowanTable() throws SQLException {
        Statement stmt = dbConnection.createStatement();

        try {
            String createTableQuery =
                    "CREATE TABLE OROWAN_OUTPUT (" +
                            "  \"CASE\" VARCHAR(255)," +
                            "  Errors VARCHAR(255)," +
                            "  Rolling_Force VARCHAR(255)," +
                            "  Forward_Slip VARCHAR(255)," +
                            "  Rolling_Torque VARCHAR(255)," +
                            "  Sigma_Moy VARCHAR(255), " +
                            "  Sigma_Ini VARCHAR(255)," +
                            "  Sigma_Out VARCHAR(255)," +
                            "  Sigma_Max VARCHAR(255)," +
                            "  Neutral_X VARCHAR(255)," +
                            "  U_Forward_Slip VARCHAR(255)," +
                            "  Contact_Length VARCHAR(255)," +
                            "  Neutral_H VARCHAR(255)," +
                            "  angle_Tresca_Bw VARCHAR(255)," +
                            "  Neutral_Angle VARCHAR(255)," +
                            "  angle_Tresca_Fw VARCHAR(255)," +
                            "  U_Rolling_Force VARCHAR(255)," +
                            "  Tresca_Presence VARCHAR(255)," +
                            "  Total_Tresca VARCHAR(255)," +
                            "  Alpha_Max VARCHAR(255)," +
                            "  Has_Converged VARCHAR(255)" +
                            ")";
            stmt.executeUpdate(createTableQuery);
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    //Add MEAN TABLE
    public static void addMeanTable() throws SQLException {
        Statement stmt = dbConnection.createStatement();

        try {
            String createTableQuery =
                    "CREATE TABLE MEAN_OROWAN (" +
                            "  \"CASE\" VARCHAR(255)," +
                            "  Rolling_Force VARCHAR(255)," +
                            "  Forward_Slip VARCHAR(255)," +
                            "  Rolling_Torque VARCHAR(255)," +
                            "  Sigma_Moy VARCHAR(255), " +
                            "  Sigma_Ini VARCHAR(255)," +
                            "  Sigma_Out VARCHAR(255)," +
                            "  Sigma_Max VARCHAR(255)," +
                            "  Neutral_X VARCHAR(255)," +
                            "  U_Forward_Slip VARCHAR(255)," +
                            "  Contact_Length VARCHAR(255)," +
                            "  Neutral_H VARCHAR(255)," +
                            "  angle_Tresca_Bw VARCHAR(255)," +
                            "  Neutral_Angle VARCHAR(255)," +
                            "  angle_Tresca_Fw VARCHAR(255)," +
                            "  U_Rolling_Force VARCHAR(255)," +
                            "  Tresca_Presence VARCHAR(255)," +
                            "  Total_Tresca VARCHAR(255)," +
                            "  Alpha_Max VARCHAR(255)" +
                            ")";
            stmt.executeUpdate(createTableQuery);
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    //Add MEAN Data
    static void addMeanData() throws SQLException, IOException {
        String csvFile = new File("").getAbsolutePath() + "/src/main/resources/com.example.backend/meanData.csv";
        String tableName = "MEAN_OROWAN";
        String sql = "INSERT INTO MEAN_OROWAN (\"CASE\", Rolling_Force, Forward_Slip, Rolling_Torque, Sigma_Moy," +
                " Sigma_Ini, Sigma_Out, Sigma_Max, Neutral_X, U_Forward_Slip, Contact_Length, Neutral_H, angle_Tresca_Bw," +
                " Neutral_Angle, angle_Tresca_Fw, U_Rolling_Force, Tresca_Presence, Total_Tresca, Alpha_Max) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement pstmt = dbConnection.prepareStatement(sql);

        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        String line;

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            for (int i = 0; i < values.length; i++) {
                pstmt.setString(i+1, values[i]);
            }
            pstmt.executeUpdate();
        }

        br.close();
        pstmt.close();
    }

    public static void deleteTable(String tableName) throws SQLException {
        Statement stmt = dbConnection.createStatement();

        try {
            String createTableQuery =
                    "DROP TABLE " + tableName;
            stmt.executeUpdate(createTableQuery);
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

}
