package infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCContext {
    public static final String URL = "jdbc:mysql://localhost:3306/janggi";
    public static final String USER = "root";
    public static final String PASSWORD = "na58745874@";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
