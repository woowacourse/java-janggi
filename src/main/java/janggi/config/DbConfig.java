package janggi.config;

import janggi.db.ConnectionManager;
import janggi.db.DatabaseInitializer;
import janggi.db.TransactionManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.h2.jdbcx.JdbcConnectionPool;

public class DbConfig implements AutoCloseable {

    private static final String INVALID_PROPERTIES = "[ERROR] db.properties 파일을 찾을 수 없습니다.";

    private final JdbcConnectionPool connectionPool;
    private final DatabaseInitializer databaseInitializer;
    private final TransactionManager transactionManager;

    public DbConfig() {
        this.connectionPool = createConnectionPool();
        ConnectionManager connectionManager = new ConnectionManager(connectionPool);
        this.databaseInitializer = new DatabaseInitializer(connectionManager);
        this.transactionManager = new TransactionManager(connectionManager);
    }

    private JdbcConnectionPool createConnectionPool() {
        Properties properties = new Properties();
        try (InputStream input = AppConfig.class.getResourceAsStream("/db.properties")) {
            validateProperties(input);
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        return JdbcConnectionPool.create(url, user, password);
    }

    private void validateProperties(InputStream input) {
        if (input == null) {
            throw new IllegalStateException(INVALID_PROPERTIES);
        }
    }

    public DatabaseInitializer databaseInitializer() {
        return databaseInitializer;
    }

    public TransactionManager transactionManager() {
        return transactionManager;
    }

    @Override
    public void close() {
        connectionPool.dispose();
    }
}
