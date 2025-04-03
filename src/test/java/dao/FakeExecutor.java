package dao;

import dao.DatabaseExecutor.SQLConsumer;
import dao.DatabaseExecutor.SQLFunction;
import domain.board.Position;
import domain.piece.Piece;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

// Executor의 Fake 구현체
public class FakeExecutor implements Executor {

    private boolean executeBatchCalled = false;
    private boolean executeUpdateCalled = false;
    private Map<Position, Piece> queryResult;

    public void setQueryResult(Map<Position, Piece> queryResult) {
        this.queryResult = queryResult;
    }

    public boolean isExecuteBatchCalled() {
        return executeBatchCalled;
    }

    public boolean isExecuteUpdateCalled() {
        return executeUpdateCalled;
    }


    @Override
    public void executeUpdate(String query, SQLConsumer<PreparedStatement> preparer) {
        executeUpdateCalled = true;
    }

    @Override
    public void executeBatch(String query, SQLConsumer<PreparedStatement> preparer) {
        executeBatchCalled = true;
    }

    @Override
    public void executeTransaction(SQLConsumer<Connection> transaction) {
        executeUpdateCalled = true;
    }

    @Override
    public <T> T executeQuery(String query, SQLFunction<PreparedStatement, ResultSet, T> handler) {
        return (T) queryResult;
    }
}
