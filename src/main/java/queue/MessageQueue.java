package queue;

import dao.init.ConnectionGenerator;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;

public class MessageQueue {

    private final Deque<Consumer<Connection>> delayedTransactions;
    private final ConnectionGenerator connectionGenerator;

    public MessageQueue(ConnectionGenerator connectionGenerator) {
        this.connectionGenerator = connectionGenerator;
        this.delayedTransactions = new ArrayDeque<>();
    }

    public void executeAllTransaction(Consumer<Connection> transaction) {
        executeDelayedTransactions();

        try (Connection connection = connectionGenerator.createConnection()) {
            connection.setAutoCommit(false);
            transaction.accept(connection);
            connection.commit();
        } catch (SQLException e) {
            delayedTransactions.add(transaction);
            throw new RuntimeException(
                    "[ERROR] DB 연결이 끊어져 트랜잭션 실행에 실패했습니다. 실패한 트랜잭션이 메시지 큐에 추가됐습니다. : " + e.getMessage());
        }
    }

    public void executeDelayedTransactions() {
        while (!delayedTransactions.isEmpty()) {
            var delayedTransaction = delayedTransactions.getFirst();

            executeTransaction(delayedTransaction);
            delayedTransactions.removeFirst();
        }
    }

    private void executeTransaction(Consumer<Connection> transaction) {
        try (Connection connection = connectionGenerator.createConnection()) {
            connection.setAutoCommit(false);
            transaction.accept(connection);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(
                    "[ERROR] DB 연결이 끊어져 트랜잭션 실행에 실패했습니다. : " + e.getMessage());
        }
    }
}
