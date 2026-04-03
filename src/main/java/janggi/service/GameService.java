package janggi.service;

import janggi.db.TransactionManager;
import janggi.db.TransactionManager.SqlConsumer;
import janggi.db.TransactionManager.SqlFunction;
import janggi.domain.Game;
import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.board.initializer.SnapshotBoardInitializer;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.repository.GamePieceRepository;
import janggi.repository.GameStateRepository;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class GameService {

    private static final String GAME_ACCESS_FAILED = "[ERROR] 게임 상태를 DB에서 처리하는 중 문제가 발생했습니다.";

    private final TransactionManager transactionManager;
    private final GameStateRepository gameStateRepository;
    private final GamePieceRepository gamePieceRepository;

    public GameService(
            TransactionManager transactionManager,
            GameStateRepository gameStateRepository,
            GamePieceRepository gamePieceRepository
    ) {
        this.transactionManager = transactionManager;
        this.gameStateRepository = gameStateRepository;
        this.gamePieceRepository = gamePieceRepository;
    }

    public Optional<Game> findById(long gameId) {
        return readOnly(connection -> {
            Optional<Camp> currentTurn = gameStateRepository.findCurrentTurnByGameId(connection, gameId);
            if (currentTurn.isEmpty()) {
                return Optional.empty();
            }

            Map<Position, Piece> boardSnapshot = gamePieceRepository.findByGameId(connection, gameId);
            Board board = new Board(new SnapshotBoardInitializer(boardSnapshot));

            return Optional.of(Game.restore(gameId, board, currentTurn.orElseThrow()));
        } catch (SQLException e) {
            throw new IllegalStateException(GAME_ACCESS_FAILED);
        }
    }

    public void save(Game game) {
        inTransaction(connection -> {
            gameStateRepository.save(connection, game.id(), game.currentTurn());
            gamePieceRepository.saveGameByBoard(
                    connection,
                    game.id(),
                    game.boardSnapshot()
            );
        });
    }

    public void deleteById(long gameId) {
        inTransaction(connection -> {
            gamePieceRepository.deleteByGameId(connection, gameId);
            gameStateRepository.deleteById(connection, gameId);
        });
    }

    private <T> T readOnly(SqlFunction<T> action) {
        try {
            return transactionManager.readOnly(action);
        } catch (SQLException e) {
            throw new IllegalStateException(GAME_ACCESS_FAILED);
        }
    }

    private <T> T inTransaction(SqlFunction<T> action) {
        try {
            return transactionManager.inTransaction(action);
        } catch (SQLException e) {
            throw new IllegalStateException(GAME_ACCESS_FAILED);
        }
    }

    private void inTransaction(SqlConsumer action) {
        try {
            transactionManager.inTransaction(action);
        } catch (SQLException e) {
            throw new IllegalStateException(GAME_ACCESS_FAILED);
        }
    }
}
