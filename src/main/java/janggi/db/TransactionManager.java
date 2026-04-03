package janggi.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

public class TransactionManager {
    private final SQLManager sqlManager;

    public TransactionManager(SQLManager sqlManager) {
        this.sqlManager = sqlManager;
    }

    public <T> T sync(Function<Connection, T> function) {
        Connection conn = sqlManager.ensureConnection();
        try {
            T result = function.apply(conn);
            conn.commit();
            return result;
        } catch (Exception e) {
            rollback(conn);
            throw new RuntimeException("Transaction failed", e);
        }
    }

    public void sync(Consumer<Connection> consumer) {
        Connection conn = sqlManager.ensureConnection();
        try {
            consumer.accept(conn);
            conn.commit();
        } catch (Exception e) {
            rollback(conn);
            throw new RuntimeException("트랜잭션 실행 중 오류 발생", e);
        }
    }

    private void rollback(Connection conn) {
        try {
            if (conn != null) conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}