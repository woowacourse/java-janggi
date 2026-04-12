package janggi.infra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import org.h2.jdbcx.JdbcConnectionPool;

public class ConnectionProvider {

    private static final Path DB_DIRECTORY = Path.of("./data");

    private final JdbcConnectionPool connectionPool;

    public ConnectionProvider(DbProperties config) {
        createDbDirectory();

        this.connectionPool = JdbcConnectionPool.create(
                config.getDbUrl(),
                config.getDbUsername(),
                config.getDbPassword()
        );

        this.connectionPool.setMaxConnections(10);
        this.connectionPool.setLoginTimeout(5);
    }

    private void createDbDirectory() {
        try {
            Files.createDirectories(DB_DIRECTORY);
        } catch (IOException e) {
            throw new IllegalStateException("DB 디렉토리 생성에 실패했습니다.", e);
        }
    }

    public Connection getConnection() {
        try {
            return connectionPool.getConnection();
        } catch (SQLException e) {
            throw new IllegalArgumentException("DB 연결에 실패했습니다.", e);
        }
    }

    public void dispose() {
        connectionPool.dispose();
    }
}
