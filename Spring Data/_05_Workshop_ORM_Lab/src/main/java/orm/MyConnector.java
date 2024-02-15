package orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyConnector {
    private static Connection connection;
    private static final String jdbcString = "jdbc:mysql://109.160.80.16:3306/";

    private MyConnector() {

    }
    public static void createConnection(String user, String password, String dbName) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);
        connection = DriverManager.getConnection(jdbcString + dbName, properties);
    }

    public static Connection getConnection(String user, String password, String dbName) throws SQLException {
        if (connection == null) {
            createConnection(user, password, dbName);
        }
        return connection;
    }

    public static void  closeConnection() throws SQLException {
        connection.close();
    }
}