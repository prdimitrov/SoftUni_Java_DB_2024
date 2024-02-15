package DBConn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    public static Connection datataseConnection(String dbName) {
        Connection connection = null;
        try {
            String url = "jdbc:mysql://109.160.80.16:3306/";
            Properties properties = new Properties();
            properties.setProperty("user", "rootuser");
            properties.setProperty("password", "12341234");
            connection = DriverManager.getConnection(url + dbName, properties);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
