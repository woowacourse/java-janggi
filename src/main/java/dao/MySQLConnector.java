package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class MySQLConnector implements Connector {
    private String server;
    private String database;
    private String option;
    private String username;
    private String password;

    public MySQLConnector() {
        loadProperties();
    }

    private void loadProperties() {
        try (final InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties")) {
            final Properties prop = new Properties();
            prop.load(input);

            server = prop.getProperty("db.server");
            database = prop.getProperty("db.database");
            option = prop.getProperty("db.options");
            username = prop.getProperty("db.username");
            password = prop.getProperty("db.password");
        } catch (IOException e) {
            throw new RuntimeException("DB 설정 파일에 오류가 발생했습니다.");
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + server + "/" + database + option, username, password);
        } catch (final SQLException e) {
            throw new SQLException("DB 연결 오류 : " + e.getMessage());
        }
    }
}
