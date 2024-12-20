//Class for Database Connection Utility

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://192.168.50.162:3306/itsok_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        // No need to explicitly load the driver in newer versions of MySQL Connector/J
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
