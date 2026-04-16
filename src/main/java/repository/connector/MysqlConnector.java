package repository.connector;

import config.DatabaseProperties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnector implements Connector {

    private static final int MAX_CONNECTION_ATTEMPTS = 10;
    private static final long CONNECTION_RETRY_DELAY_MILLIS = 1000L;

    private final DatabaseProperties databaseProperties;

    public MysqlConnector(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    @Override
    public Connection getConnection() throws SQLException {
        SQLException lastException = null;

        for (int attempt = 1; attempt <= MAX_CONNECTION_ATTEMPTS; attempt++) {
            try {
                return DriverManager.getConnection(
                    databaseProperties.url(),
                    databaseProperties.user(),
                    databaseProperties.password()
                );
            } catch (SQLException e) {
                lastException = e;
                if (!isRetryable(e) || attempt == MAX_CONNECTION_ATTEMPTS) {
                    throw e;
                }
                sleepBeforeRetry();
            }
        }
        throw lastException;
    }

    private boolean isRetryable(SQLException e) {
        String sqlState = e.getSQLState();
        return sqlState != null && sqlState.startsWith("08");
    }

    private void sleepBeforeRetry() {
        try {
            Thread.sleep(CONNECTION_RETRY_DELAY_MILLIS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("DB 연결 재시도 중 인터럽트가 발생했습니다.", e);
        }
    }
}
