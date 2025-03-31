package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {

    private static final String SERVER = "localhost:3306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            StackTraceElement[] stackTrace = e.getStackTrace();
            StringBuilder filteredStackTrace = new StringBuilder();

            for (StackTraceElement element : stackTrace) {
                filteredStackTrace.append("Class: ").append(element.getClassName())
                        .append(", Method: ").append(element.getMethodName())
                        .append(", Line: ").append(element.getLineNumber())
                        .append("\n");
            }

            System.err.println("Filtered StackTrace: " + filteredStackTrace);
            return null;
        }
    }
}

