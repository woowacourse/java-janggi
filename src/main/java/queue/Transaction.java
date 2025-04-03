package queue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

public class Transaction {

    private final List<DelayedQuery> delayedQueries;

    public Transaction() {
        this.delayedQueries = new ArrayList<>();
    }

    public void addLast(DelayedQuery delayedQuery) {
        delayedQueries.add(delayedQuery);
    }

    public void executeTransaction(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        for (DelayedQuery delayedQuery : delayedQueries) {
            executeQuery(connection, delayedQuery.sql(), delayedQuery.params());
        }
        connection.commit();
    }

    private void executeQuery(Connection connection, String sql, List<Object> params) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }
            preparedStatement.executeUpdate();
        } catch (SQLSyntaxErrorException e) {
            throw new IllegalArgumentException("[ERROR]: 잘못된 형식의 쿼리문입니다. " + sql);
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] DB 연결이 끊어졌습니다.");
        }
    }
}
