package transaction;

import java.sql.SQLException;
import java.util.function.Supplier;
import repository.connector.TransactionalConnector;

public class JdbcTransactionManager implements TransactionManager {

    private static final String TRANSACTION_FAILED_MESSAGE = "트랜잭션 처리에 실패했습니다.";
    private static final String TRANSACTION_CLOSE_FAILED_MESSAGE = "트랜잭션 정리에 실패했습니다.";

    private final TransactionalConnector connector;

    public JdbcTransactionManager(TransactionalConnector connector) {
        this.connector = connector;
    }

    @Override
    public <T> T execute(Supplier<T> action) {
        if (connector.hasActiveTransaction()) {
            return action.get();
        }

        boolean transactionStarted = false;
        try {
            connector.beginTransaction();
            transactionStarted = true;
            T result = action.get();
            connector.commitTransaction();
            return result;
        } catch (SQLException e) {
            rollbackOnFailure(e, transactionStarted);
            throw new IllegalStateException(TRANSACTION_FAILED_MESSAGE, e);
        } catch (RuntimeException | Error e) {
            rollbackOnFailure(e, transactionStarted);
            throw e;
        } finally {
            closeTransaction(transactionStarted);
        }
    }

    private void rollbackOnFailure(Throwable cause, boolean transactionStarted) {
        if (!transactionStarted) {
            return;
        }

        try {
            connector.rollbackTransaction();
        } catch (SQLException rollbackException) {
            cause.addSuppressed(rollbackException);
        }
    }

    private void closeTransaction(boolean transactionStarted) {
        if (!transactionStarted) {
            return;
        }

        try {
            connector.closeTransaction();
        } catch (SQLException e) {
            throw new IllegalStateException(TRANSACTION_CLOSE_FAILED_MESSAGE, e);
        }
    }
}
