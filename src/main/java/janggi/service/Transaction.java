package janggi.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;

public class Transaction {

    public void execute(final Connection connection, final Consumer<Connection> command) {
        try {
            connection.setAutoCommit(false);
            command.accept(connection);
            connection.commit();
        } catch (final Exception e) {
            tryRollback(connection, e);
            throw new RuntimeException("트랜잭션 실패, 롤백 진행", e);
        }
    }

    private void tryRollback(final Connection connection, final Exception e) {
        try {
            connection.rollback();
        } catch (final SQLException rollbackException) {
            e.addSuppressed(rollbackException);
        }
    }
}
