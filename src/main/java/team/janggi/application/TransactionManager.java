package team.janggi.application;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class TransactionManager {
    private final ConnectionManager connectionManager;
    private final ConcurrentHashMap<Long, Connection> transactionConnections;

    public TransactionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.transactionConnections = new ConcurrentHashMap<>();
    }

    public <T> T execute(Supplier<T> transactionalCode) {
        final Connection connection = getTransaction();
        try {
            T result = transactionalCode.get();
            connection.commit();
            return result;
        } catch (SQLException e) {
            rollback(connection);
            throw new RuntimeException("트랜잭션 실행에 실패하였습니다.", e);
        } catch (Exception e) {
            rollback(connection);
            throw e;
        } finally {
            close(connection);
        }
    }

    public void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException("롤백에 실패하였습니다.", e);
        }
    }

    public void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결을 닫는 과정에서 오류가 발생하였습니다.", e);
        } finally {
            transactionConnections.remove(Thread.currentThread().threadId());
        }
    }

    public Connection getTransaction() {
        return getConnection(true);
    }

    public Connection getConnection() {
        return getConnection(false);
    }


    private Connection getConnection(boolean useTransaction) {
        Connection connection = transactionConnections.get(Thread.currentThread().threadId());
        if (connection != null) {
            return connection;
        }

        connection = connectionManager.getConnection();

        if (useTransaction) {
            transactionConnections.put(Thread.currentThread().threadId(), connection);
        }

        try {
            connection.setAutoCommit(!useTransaction);
            return connection;
        } catch (Exception e) {
            transactionConnections.remove(Thread.currentThread().threadId());
            throw new RuntimeException(e);
        }
    }
}
