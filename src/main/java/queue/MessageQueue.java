package queue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class MessageQueue {

    private static final Deque<DelayedQuery> delayedQueries = new ArrayDeque<>();

    public static void addLast(DelayedQuery delayedQuery) {
        delayedQueries.addLast(delayedQuery);
    }

    public static void executeDelayedQueries(Connection connection) {
        while (!delayedQueries.isEmpty()) {
            var delayedQuery = delayedQueries.getFirst();

            try {
                executeQuery(connection, delayedQuery.sql(), delayedQuery.params());
                delayedQueries.removeFirst();
            } catch (RuntimeException e) {
                break;
            }
        }
    }

    private static void executeQuery(Connection connection, String sql, List<Object> params) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // 의도적 무시
        }
    }
}
