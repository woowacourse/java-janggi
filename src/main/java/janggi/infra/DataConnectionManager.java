package janggi.infra;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.h2.jdbcx.JdbcConnectionPool;

public class DataConnectionManager {

    private static final int POOL_SIZE = 10;

    private final JdbcConnectionPool connectionPool;

    public DataConnectionManager() {
        try {
            Properties properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);
            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");
            connectionPool = JdbcConnectionPool.create(url, username, password);
            connectionPool.setMaxConnections(POOL_SIZE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }
}
