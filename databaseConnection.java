//Class for Database Connection Utility

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/my_project_db";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    public static Connection getConnection() throws SQLException {
        // No need to explicitly load the driver in newer versions of MySQL Connector/J
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
