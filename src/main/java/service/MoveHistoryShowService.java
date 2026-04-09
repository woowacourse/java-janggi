package service;

import db.jdbc.ConnectionManager;
import db.jdbc.SqlConnection;
import db.jdbc.SqlConnectionWrapper;
import db.repository.JanggiGameRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import core.MoveHistory;
import view.SelectedGame;
import view.JanggiView;

public class MoveHistoryShowService {

    private final JanggiView view;
    private final ConnectionManager connectionManager;
    private final JanggiGameRepository repository;

    public MoveHistoryShowService(
        final JanggiView view,
        final ConnectionManager connectionManager,
        final JanggiGameRepository repository
    ) {
        this.view = view;
        this.connectionManager = connectionManager;
        this.repository = repository;
    }

    public void show() {
        final List<GameSummary> savedGames = executeReadOnly(
            repository::findTop10GameRoomsOrderByCreatedAtDesc
        );

        final SelectedGame selectedGameId = view.askGameId(savedGames);

        List<MoveHistory> moveHistories = executeReadOnly(connection ->
            repository.findMoveHistoriesByGameId(connection, selectedGameId.getId())
        );
        view.printMoveHistories(moveHistories);
    }

    private <T> T executeReadOnly(final SqlConnectionOperation<T> operation) {
        try (final Connection connection = connectionManager.getConnection()) {
            return operation.execute(new SqlConnectionWrapper(connection));
        } catch (SQLException e) {
            throw new IllegalStateException("DB 연결에 실패했습니다.", e);
        } catch (Exception e) {
            throw new IllegalStateException("DB 조회에 실패했습니다.", e);
        }
    }

    @FunctionalInterface
    interface SqlConnectionOperation<T> {
        T execute(SqlConnection connection) throws Exception;
    }
}
