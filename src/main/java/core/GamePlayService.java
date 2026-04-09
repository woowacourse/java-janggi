package core;

import db.jdbc.ConnectionManager;
import db.jdbc.SqlConnection;
import db.jdbc.SqlConnectionWrapper;
import db.repository.JanggiGameRepository;
import java.sql.Connection;
import java.sql.SQLException;
import movepolicy.MoveHistory;
import position.Position;

public class GamePlayService {

    private final ConnectionManager connectionManager;
    private final JanggiGameRepository repository;

    public GamePlayService(final ConnectionManager connectionManager, final JanggiGameRepository repository) {
        this.connectionManager = connectionManager;
        this.repository = repository;
    }

    public void run(final Long gameId, final GameSession session) {
        while (session.isPlaying()) {
            final GameTurnResult turnResult = session.run();
            saveTurn(gameId, turnResult);
        }
        session.printResult();
    }

    private void saveTurn(final Long gameId, final GameTurnResult turnResult) {
        executeInTransaction(connection -> {
            repository.update(connection, gameId, turnResult.updatedGame());
            if (turnResult.hasNoPieceMove()) {
                return;
            }

            final MoveHistory moveHistory = turnResult.moveHistory();
            final Position destination = moveHistory.destination();

            if (moveHistory.isCaptured()) {
                repository.deletePiece(connection, gameId, destination);
            }
            repository.updatePiecePosition(
                connection,
                gameId,
                moveHistory.departure(),
                destination
            );
            repository.saveMoveHistory(connection, gameId, moveHistory);
        });
    }

    private void executeInTransaction(final SqlConnectionOperation operation) {
        try (final Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try {
                operation.execute(new SqlConnectionWrapper(connection));
                connection.commit();
            } catch (final Exception e) {
                connection.rollback();
                throw new IllegalStateException("DB 작업에 실패했습니다.", e);
            }
        } catch (final SQLException e) {
            throw new IllegalStateException("DB 연결에 실패했습니다.", e);
        }
    }

    @FunctionalInterface
    interface SqlConnectionOperation {
        void execute(SqlConnection connection) throws Exception;
    }
}
