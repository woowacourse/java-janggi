package janggi.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLDBConnector implements DBConnector {
    private static final Properties properties = PropertiesLoader.load();

    private static final String SERVER = properties.getProperty("DB_HOST") + ":" + properties.getProperty("DB_PORT");
    private static final String DATABASE = properties.getProperty("DB_NAME");
    private static final String OPTIONS = properties.getProperty("DB_OPTIONS");
    private static final String USERNAME = properties.getProperty("DB_USER");
    private static final String PASSWORD = properties.getProperty("DB_PASSWORD");

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTIONS, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new IllegalArgumentException("[ERROR] : 운영 DB 연결 실패", e);
        }
    }
}

