package janggi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {
    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    private static Connection janggiConnection;
    private static Connection dbConnection;

    public Connection getJanggiConnection() {
        try {
            if (janggiConnection == null || janggiConnection.isClosed()) {
                String url = String.format("jdbc:mysql://%s/%s%s", SERVER, DATABASE, OPTION);
                janggiConnection = DriverManager.getConnection(url, USERNAME, PASSWORD);
            }
            return janggiConnection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        try {
            if (dbConnection == null || dbConnection.isClosed()) {
                String url = String.format("jdbc:mysql://%s/%s", SERVER, OPTION);
                dbConnection = DriverManager.getConnection(url, USERNAME, PASSWORD);
            }
            return dbConnection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeUpdate(String sql, String... values) {
        try (PreparedStatement statement = generatePreparedStatement(sql, values)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PreparedStatement generatePreparedStatement(String sql, String... values) throws SQLException {
        PreparedStatement statement = getJanggiConnection().prepareStatement(sql);
        for (int i = 0; i < values.length; ++i) {
            statement.setString(i + 1, values[i]);
        }
        return statement;
    }
}
