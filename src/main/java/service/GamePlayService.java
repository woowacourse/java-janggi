package service;

import board.SangSetupType;
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
import pieces.Piece;
import pieces.Side;
import position.Position;
import util.Retry;
import view.DisplayBoard;
import view.JanggiView;
import view.SelectedGame;

public class GamePlayService {

    private final JanggiView view;
    private final ConnectionManager connectionManager;
    private final JanggiGameRepository repository;

    public GamePlayService(
        final JanggiView view,
        final ConnectionManager connectionManager,
        final JanggiGameRepository repository
    ) {
        this.view = view;
        this.connectionManager = connectionManager;
        this.repository = repository;
    }

    public void play() {
        final PreparedGame prepared = prepare();
        run(prepared.gameId(), prepared.game(), prepared.initialMoveCount());
    }

    private PreparedGame prepare() {
        final List<GameSummary> savedGames = executeReadOnly(
            repository::findTop10GameRoomsOrderByCreatedAtDesc
        );
        if (savedGames.isEmpty() || view.askNewGame()) {
            return createNewGame();
        }

        final SelectedGame selectedGame = Retry.untilSuccess(() -> {
            final SelectedGame gameId = view.askGameId(savedGames);
            if (gameId.isOver()) {
                throw new IllegalArgumentException("종료된 게임은 실행할 수 없습니다.");
            }
            return gameId;
        });
        return findSavedGame(selectedGame);
    }

    private PreparedGame createNewGame() {
        final SangSetupType choSangSetupType = view.askSangSetupUntilSuccess(Side.CHO);
        final SangSetupType hanSangSetupType = view.askSangSetupUntilSuccess(Side.HAN);
        final JanggiGame game = JanggiGame.of(choSangSetupType, hanSangSetupType);

        final Long gameId = executeInTransaction(connection -> repository.saveGame(connection, game));
        return new PreparedGame(gameId, game, 0);
    }

    private PreparedGame findSavedGame(final SelectedGame selectedGame) {
        return executeReadOnly(connection -> {
            final JanggiGame game = repository.findGameById(connection, selectedGame.getId())
                .orElseThrow(() -> new IllegalArgumentException("선택한 게임이 존재하지 않습니다."));
            final int moveCount = repository.findMoveHistoriesByGameId(connection, selectedGame.getId()).size();

            return new PreparedGame(selectedGame.getId(), game, moveCount);
        });
    }

    private void run(final Long gameId, JanggiGame game, int moveCount) {
        while (!game.isOver()) {
            final GameTurnResult result = playOneTurn(game, moveCount > 0);

            if (result.undoRequested()) {
                game = executeInTransaction(connection -> {
                    repository.undoLastMove(connection, gameId);
                    return repository.findGameById(connection, gameId)
                        .orElseThrow(() -> new IllegalStateException("무르기 후 게임 조회에 실패했습니다."));
                });
                moveCount--;
            } else {
                saveTurn(gameId, result);
                game = result.updatedGame();
                if (!result.hasNoPieceMove()) {
                    moveCount++;
                }
            }
        }
        view.printGameResult(game.getResult());
    }

    private GameTurnResult playOneTurn(final JanggiGame game, final boolean canUndo) {
        return Retry.untilSuccess(() -> {
            printGameStatus(game);

            if (canUndo && view.askUndoRequest(game.getTurnSide())) {
                return GameTurnResult.undoRequested(game);
            }

            if (view.askEndByScore(game.getTurnSide())) {
                return GameTurnResult.endByScore(game.endByScore());
            }

            return move(game);
        });
    }

    private void printGameStatus(final JanggiGame game) {
        view.printBoard(DisplayBoard.of(game.getBoard()));
        final Side turnSide = game.getTurnSide();
        view.printTurnSide(turnSide);
        view.printScore(turnSide, game.calculateScoreOf(turnSide));
        view.printScore(turnSide.other(), game.calculateScoreOf(turnSide.other()));
    }

    private GameTurnResult move(final JanggiGame game) {
        final Position departure = view.askDeparture();
        final Position destination = view.askDestination();
        final Piece movingPiece = game.getPieceAt(departure);
        final Piece capturedPiece = game.getPieceAt(destination);
        final JanggiGame updated = game.move(departure, destination);
        return GameTurnResult.move(updated, new MoveHistory(departure, destination, movingPiece, capturedPiece));
    }

    private void saveTurn(final Long gameId, final GameTurnResult turnResult) {
        executeInTransaction(connection -> {
            repository.updateGame(connection, gameId, turnResult.updatedGame());
            if (turnResult.hasNoPieceMove()) {
                return null;
            }

            final MoveHistory moveHistory = turnResult.moveHistory();
            repository.updatePiecePosition(connection, gameId, moveHistory.departure(), moveHistory.destination());
            repository.saveMoveHistory(connection, gameId, moveHistory);
            return null;
        });
    }

    private <T> T executeReadOnly(final SqlConnectionOperation<T> operation) {
        try (final Connection connection = connectionManager.getConnection()) {
            return operation.execute(new SqlConnectionWrapper(connection));
        } catch (final SQLException e) {
            throw new IllegalStateException("DB 연결에 실패했습니다.", e);
        } catch (final Exception e) {
            throw new IllegalStateException("DB 조회에 실패했습니다.", e);
        }
    }

    private <T> T executeInTransaction(final SqlConnectionOperation<T> operation) {
        try (final Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try {
                final T result = operation.execute(new SqlConnectionWrapper(connection));
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
