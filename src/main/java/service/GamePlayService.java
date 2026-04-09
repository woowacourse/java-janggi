package service;

import board.SangSetupType;
import core.GameSession;
import core.GameSummary;
import core.GameTurnResult;
import core.JanggiGame;
import core.PreparedGame;
import db.jdbc.ConnectionManager;
import db.jdbc.SqlConnection;
import db.jdbc.SqlConnectionWrapper;
import db.repository.JanggiGameRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import movepolicy.MoveHistory;
import pieces.Side;
import position.Position;
import util.Retry;
import view.SelectedGame;
import view.JanggiView;

public class GamePlayService {

    private final JanggiView view;
    private final ConnectionManager connectionManager;
    private final JanggiGameRepository repository;

    public GamePlayService(JanggiView view, final ConnectionManager connectionManager, final JanggiGameRepository repository) {
        this.view = view;
        this.connectionManager = connectionManager;
        this.repository = repository;
    }

    private PreparedGame prepare() {
        final List<GameSummary> savedGames = executeReadOnly(
            repository::findTop10GameRoomsOrderByCreatedAtDesc
        );

        if (savedGames.isEmpty()) {
            return createNewGame();
        }

        if (view.askNewGame()) {
            return createNewGame();
        }

        final SelectedGame selectedGameId = Retry.untilSuccess(() -> {
            final SelectedGame gameId = view.askGameId(savedGames);
            if (gameId.isOver()) {
                throw new IllegalArgumentException("종료된 게임은 실행할 수 없습니다.");
            }
            return gameId;
        });
        return findSavedGame(selectedGameId);
    }

    public void play() {
        PreparedGame preparedGame = prepare();
        run(preparedGame.gameId(), preparedGame.session());
    }

    private PreparedGame createNewGame() {
        final SangSetupType choSangSetupType = view.askSangSetupUntilSuccess(Side.CHO);
        final SangSetupType hanSangSetupType = view.askSangSetupUntilSuccess(Side.HAN);
        final JanggiGame game = JanggiGame.of(choSangSetupType, hanSangSetupType);

        final Long gameId = executeInTransaction(connection -> repository.saveGame(connection, game));
        return new PreparedGame(gameId, new GameSession(game, view));
    }

    private PreparedGame findSavedGame(final SelectedGame selectedGameId) {
        final JanggiGame game = executeReadOnly(
            connection -> repository.findGameById(connection, selectedGameId.id())
                .orElseThrow(() -> new IllegalArgumentException("선택한 게임이 존재하지 않습니다."))
        );
        return new PreparedGame(selectedGameId.id(), new GameSession(game, view));
    }

    private void run(final Long gameId, final GameSession session) {
        while (!session.isOver()) {
            final GameTurnResult turnResult = session.playTurn();
            if (turnResult.unDo()) {
                continue;
            }
            saveTurn(gameId, turnResult);
        }
        session.printResult();
    }

    private void saveTurn(final Long gameId, final GameTurnResult turnResult) {
        executeInTransaction(connection -> {
            repository.updateGame(connection, gameId, turnResult.updatedGame());
            if (turnResult.hasNoPieceMove()) {
                return null;
            }

            final MoveHistory moveHistory = turnResult.moveHistory();
            final Position destination = moveHistory.destination();

            repository.updatePiecePosition(
                connection,
                gameId,
                moveHistory.departure(),
                destination
            );
            repository.saveMoveHistory(connection, gameId, moveHistory);
            return null;
        });
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

    private <T> T executeInTransaction(final SqlConnectionOperation<T> operation) {
        try (final Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try {
                T result = operation.execute(new SqlConnectionWrapper(connection));
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

    @FunctionalInterface
    interface SqlConnectionOperation<T> {
        T execute(SqlConnection connection) throws Exception;
    }
}
