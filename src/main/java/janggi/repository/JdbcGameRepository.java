package janggi.repository;

import janggi.db.ConnectionFactory;
import janggi.domain.board.Position;
import janggi.domain.game.JanggiGame;
import janggi.domain.piece.Piece;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Optional;

public class JdbcGameRepository implements GameRepository {
    private final ConnectionFactory connectionFactory;

    public JdbcGameRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public long saveNewGame(JanggiGame janggiGame) {
        try (Connection connection = connectionFactory.create()) {
            return saveNewGameWithTransaction(connection, janggiGame);
        } catch (SQLException exception) {
            throw new IllegalStateException("새 게임 저장에 실패했습니다.", exception);
        }
    }

    @Override
    public Optional<SavedGame> findPlayingGame() {
        return Optional.empty();
    }

    @Override
    public void updateAfterMove(long savedGameId, JanggiGame janggiGame, Position startPiecePosition,
                                Position endPiecePosition) {
        try (Connection connection = connectionFactory.create()) {
            updateAfterMoveWithTransaction(connection, savedGameId, janggiGame, startPiecePosition,
                    endPiecePosition);
        } catch (SQLException exception) {
            throw new IllegalStateException("수 반영 저장에 실패했습니다.", exception);
        }
    }

    private long saveNewGameWithTransaction(Connection connection, JanggiGame janggiGame) throws SQLException {
        try {
            connection.setAutoCommit(false);

            long savedGameId = saveGameState(connection, janggiGame);
            saveAllPieces(connection, savedGameId, janggiGame);
            connection.commit();
            return savedGameId;
        } catch (SQLException exception) {
            rollback(connection);
            throw exception;
        }
    }

    private void updateAfterMoveWithTransaction(Connection connection, long savedGameId, JanggiGame janggiGame,
                                                Position startPiecePosition,
                                                Position endPiecePosition) throws SQLException {
        try {
            connection.setAutoCommit(false);
            updateGameState(connection, savedGameId, janggiGame);
            deletePieceOn(connection, savedGameId, endPiecePosition);
            deletePieceOn(connection, savedGameId, startPiecePosition);
            saveMovedPiece(connection, savedGameId, janggiGame, endPiecePosition);
            connection.commit();
        } catch (SQLException exception) {
            rollback(connection);
            throw exception;
        }
    }

    private long saveGameState(Connection connection, JanggiGame janggiGame) throws SQLException {
        String sql = "INSERT INTO games(turn, status) VALUES(?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, janggiGame.currentTurnTeam().name());
            statement.setString(2, findStatus(janggiGame));
            statement.executeUpdate();
            return findGeneratedGameId(statement);
        }
    }

    private void saveAllPieces(Connection connection, long savedGameId, JanggiGame janggiGame) throws SQLException {
        String sql = "INSERT INTO pieces(game_id, x, y, name, team) VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Map.Entry<Position, Piece> entry : janggiGame.board().getBoard().entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();
                statement.setLong(1, savedGameId);
                statement.setInt(2, position.x());
                statement.setInt(3, position.y());
                statement.setString(4, piece.getName().name());
                statement.setString(5, piece.getTeam().name());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private long findGeneratedGameId(PreparedStatement statement) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (!generatedKeys.next()) {
                throw new IllegalStateException("저장된 게임 id를 찾을 수 없습니다.");
            }
            return generatedKeys.getLong(1);
        }
    }

    private void updateGameState(Connection connection, long savedGameId, JanggiGame janggiGame) throws SQLException {
        String sql = "UPDATE games SET turn = ?, status = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, janggiGame.currentTurnTeam().name());
            statement.setString(2, findStatus(janggiGame));
            statement.setLong(3, savedGameId);
            statement.executeUpdate();
        }
    }

    private void deletePieceOn(Connection connection, long savedGameId, Position piecePosition) throws SQLException {
        String sql = "DELETE FROM pieces WHERE game_id = ? AND x = ? AND y = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, savedGameId);
            statement.setInt(2, piecePosition.x());
            statement.setInt(3, piecePosition.y());
            statement.executeUpdate();
        }
    }

    private void saveMovedPiece(Connection connection, long savedGameId, JanggiGame janggiGame,
                                Position endPiecePosition) throws SQLException {
        String sql = "INSERT INTO pieces(game_id, x, y, name, team) VALUES(?, ?, ?, ?, ?)";
        Piece movedPiece = janggiGame.board().findPiece(endPiecePosition);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, savedGameId);
            statement.setInt(2, endPiecePosition.x());
            statement.setInt(3, endPiecePosition.y());
            statement.setString(4, movedPiece.getName().name());
            statement.setString(5, movedPiece.getTeam().name());
            statement.executeUpdate();
        }
    }

    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException exception) {
            throw new IllegalStateException("트랜잭션 롤백에 실패했습니다.", exception);
        }
    }

    private String findStatus(JanggiGame janggiGame) {
        if (janggiGame.isPlaying()) {
            return "PLAYING";
        }
        return "FINISHED";
    }
}
