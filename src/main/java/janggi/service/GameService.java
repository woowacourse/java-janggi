package janggi.service;

import janggi.db.H2ConnectionManager;
import janggi.domain.Game;
import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.board.initializer.SnapshotBoardInitializer;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.repository.GamePieceRepository;
import janggi.repository.GameStateRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public final class GameService {

    private static final String GAME_ACCESS_FAILED = "[ERROR] 게임 상태를 DB에서 처리하는 중 문제가 발생했습니다.";

    private final GameStateRepository gameStateRepository;
    private final GamePieceRepository gamePieceRepository;

    public GameService(GameStateRepository gameStateRepository, GamePieceRepository gamePieceRepository) {
        this.gameStateRepository = gameStateRepository;
        this.gamePieceRepository = gamePieceRepository;
    }

    public Optional<Game> findById(long gameId) {
        try (Connection connection = H2ConnectionManager.getConnection()) {
            Optional<Camp> currentTurn = gameStateRepository.findById(connection, gameId);
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
        try (Connection connection = H2ConnectionManager.getConnection()) {
            connection.setAutoCommit(false);

            try {
                gameStateRepository.save(connection, game.id(), game.currentTurn());
                gamePieceRepository.replaceByGameId(
                        connection,
                        game.id(),
                        game.boardSnapshot()
                );
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(GAME_ACCESS_FAILED);
        }
    }

    public void deleteById(long gameId) {
        try (Connection connection = H2ConnectionManager.getConnection()) {
            connection.setAutoCommit(false);

            try {
                gamePieceRepository.deleteByGameId(connection, gameId);
                gameStateRepository.deleteById(connection, gameId);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(GAME_ACCESS_FAILED);
        }
    }
}
