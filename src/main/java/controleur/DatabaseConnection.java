package controleur;

import org.h2.jdbcx.*;

import java.sql.*;


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


    public static void main(String[] a) throws Exception {
        DatabaseConnection manager = DatabaseConnection.getInstance();
        manager.openDBConnection();
    }
}
