package queue;

import dao.init.ConnectionGenerator;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;

public class MessageQueue {

    private final Deque<Transaction> delayedTransactions;
    private final ConnectionGenerator connectionGenerator;

    public MessageQueue(ConnectionGenerator connectionGenerator) {
        this.connectionGenerator = connectionGenerator;
        this.delayedTransactions = new ArrayDeque<>();
    }

    public void addLast(Transaction transaction) {
        delayedTransactions.addLast(transaction);
    }

    public void executeTransactions() {
        Connection connection = connectionGenerator.createConnection();

        while (!delayedTransactions.isEmpty()) {
            var delayedTransaction = delayedTransactions.getFirst();

            try {
                delayedTransaction.executeTransaction(connection);
                delayedTransactions.removeFirst();
            } catch (SQLException e) {
                break;
            }
        }
    }

    public int size() {
        return delayedTransactions.size();
    }

    public void clear() {
        delayedTransactions.clear();
    }
}
