package core;

import board.SangSetupType;
import db.jdbc.ConnectionManager;
import db.jdbc.SqlConnection;
import db.jdbc.SqlConnectionWrapper;
import db.repository.JanggiGameRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;
import pieces.Side;
import view.InputGameId;
import view.JanggiView;

public class GamePreparationService {

    private final JanggiView view;
    private final JanggiGameRepository repository;
    private final ConnectionManager connectionManager;

    public GamePreparationService(
        final JanggiView view,
        final JanggiGameRepository repository,
        final ConnectionManager connectionManager
    ) {
        this.view = view;
        this.repository = repository;
        this.connectionManager = connectionManager;
    }

    public PreparedGame prepare() {
        final List<GameSummary> savedGames = executeReadOnly(
            repository::findTop10GameRoomsOrderByCreatedAtDesc
        );

        if (savedGames.isEmpty()) {
            return createNewGame();
        }

        final InputGameId selectedGameId = view.askGameId(savedGames);
        if (selectedGameId.isNewGame()) {
            return createNewGame();
        }
        return findSavedGame(selectedGameId);
    }

    private PreparedGame createNewGame() {
        final SangSetupType choSangSetupType = view.askSangSetupUntilSuccess(Side.CHO);
        final SangSetupType hanSangSetupType = view.askSangSetupUntilSuccess(Side.HAN);
        final JanggiGame game = JanggiGame.of(choSangSetupType, hanSangSetupType);

        final Long gameId = executeInTransaction(connection -> repository.save(connection, game));
        return new PreparedGame(gameId, new GameSession(game, view));
    }

    private PreparedGame findSavedGame(final InputGameId selectedGameId) {
        final JanggiGame game = executeReadOnly(
            connection -> repository.findById(connection, selectedGameId.id())
                .orElseThrow(() -> new IllegalArgumentException("선택한 게임이 존재하지 않습니다."))
        );
        return new PreparedGame(selectedGameId.id(), new GameSession(game, view));
    }

    private <T> T executeReadOnly(final Function<SqlConnection, T> operation) {
        try (final Connection connection = connectionManager.getConnection()) {
            return operation.apply(new SqlConnectionWrapper(connection));
        } catch (SQLException e) {
            throw new IllegalStateException("DB 연결에 실패했습니다.", e);
        } catch (Exception e) {
            throw new IllegalStateException("DB 조회에 실패했습니다.", e);
        }
    }

    private <T> T executeInTransaction(final Function<SqlConnection, T> operation) {
        try (final Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try {
                final T result = operation.apply(new SqlConnectionWrapper(connection));
                connection.commit();
                return result;
            } catch (final Exception e) {
                connection.rollback();
                throw new IllegalStateException("DB 작업에 실패했습니다.", e);
            }
        } catch (final SQLException e) {
            throw new IllegalStateException("DB 연결에 실패했습니다.", e);
        }
    }
}
