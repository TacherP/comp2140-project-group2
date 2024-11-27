//Class for Database Connection Utility

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/group2_project_db";
    private static final String USER = "root";
    private static final String PASSWORD = "P9kQ6&zR8Ls4u@JXtH";

    public static Connection getConnection() throws SQLException {
        // No need to explicitly load the driver in newer versions of MySQL Connector/J
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
