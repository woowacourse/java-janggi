package queue;

import dao.init.ConnectionGenerator;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;

public class MessageQueue {

    private final Deque<Transaction<Connection>> delayedTransactions;
    private final ConnectionGenerator connectionGenerator;

    public MessageQueue(ConnectionGenerator connectionGenerator) {
        this.connectionGenerator = connectionGenerator;
        this.delayedTransactions = new ArrayDeque<>();
    }

    public void flushQueueAndExecuteTransaction(Transaction<Connection> transaction) {
        executeDelayedTransactions();

        try (Connection connection = connectionGenerator.createConnection()) {
            connection.setAutoCommit(false);
            transaction.accept(connection);
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            delayedTransactions.add(transaction);
            throw new RuntimeException(
                    e.getMessage() + ": \n[ERROR] DB 연결이 끊어져 트랜잭션 실행에 실패했습니다. 실패한 트랜잭션이 메시지 큐에 추가됐습니다.");
        }
    }

    public void executeDelayedTransactions() {
        while (!delayedTransactions.isEmpty()) {
            var delayedTransaction = delayedTransactions.getFirst();

            executeTransaction(delayedTransaction);
            delayedTransactions.removeFirst();
        }
    }

    private void executeTransaction(Transaction<Connection> transaction) {
        try (Connection connection = connectionGenerator.createConnection()) {
            connection.setAutoCommit(false);
            transaction.accept(connection);
            connection.commit();
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(
                    e.getMessage() + ": \n[ERROR] DB 연결이 끊어져 트랜잭션 실행에 실패했습니다.");
        }
    }

    @FunctionalInterface
    public interface Transaction<T extends Connection> {
        void accept(T t) throws SQLException;
    }
}