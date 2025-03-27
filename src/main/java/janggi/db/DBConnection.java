package janggi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String SERVER = "localhost:3306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "gustn346!@";

    public Connection getJanggiConnection() {
        String url =  String.format("jdbc:mysql://%s/%s%s", SERVER, DATABASE, OPTION);
        try {
            return DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Connection getConnection() {
        String url =  String.format("jdbc:mysql://%s/%s", SERVER, OPTION);
        try {
            return DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
