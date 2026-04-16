package repository.connector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import config.DatabaseProperties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnector implements TransactionalConnector {

    private static final int MAX_CONNECTION_ATTEMPTS = 10;
    private static final long CONNECTION_RETRY_DELAY_MILLIS = 1000L;

    private final DatabaseProperties databaseProperties;
    private final ThreadLocal<Connection> transactionConnection = new ThreadLocal<>();
    private final ThreadLocal<Connection> transactionConnectionProxy = new ThreadLocal<>();

    public MysqlConnector(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (hasActiveTransaction()) {
            return transactionConnectionProxy.get();
        }
        return createNewConnection();
    }

    @Override
    public void beginTransaction() throws SQLException {
        if (hasActiveTransaction()) {
            throw new IllegalStateException("이미 진행 중인 트랜잭션이 있습니다.");
        }

        Connection connection = createNewConnection();
        connection.setAutoCommit(false);
        transactionConnection.set(connection);
        transactionConnectionProxy.set(createTransactionConnectionProxy(connection));
    }

    @Override
    public void commitTransaction() throws SQLException {
        Connection connection = requireTransactionConnection();
        connection.commit();
    }

    @Override
    public void rollbackTransaction() throws SQLException {
        Connection connection = requireTransactionConnection();
        connection.rollback();
    }

    @Override
    public void closeTransaction() throws SQLException {
        Connection connection = transactionConnection.get();
        transactionConnection.remove();
        transactionConnectionProxy.remove();

        if (connection == null) {
            return;
        }

        try {
            connection.setAutoCommit(true);
        } finally {
            connection.close();
        }
    }

    @Override
    public boolean hasActiveTransaction() {
        return transactionConnection.get() != null;
    }

    private Connection createNewConnection() throws SQLException {
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

    private Connection requireTransactionConnection() {
        Connection connection = transactionConnection.get();
        if (connection == null) {
            throw new IllegalStateException("진행 중인 트랜잭션이 없습니다.");
        }
        return connection;
    }

    private Connection createTransactionConnectionProxy(Connection connection) {
        return (Connection) Proxy.newProxyInstance(
            Connection.class.getClassLoader(),
            new Class<?>[] {Connection.class},
            (proxy, method, args) -> {
                if ("close".equals(method.getName())) {
                    return null;
                }

                try {
                    return method.invoke(connection, args);
                } catch (InvocationTargetException e) {
                    throw e.getCause();
                }
            }
        );
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
