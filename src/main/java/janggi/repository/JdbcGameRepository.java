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
            connection.setAutoCommit(false);

            long gameId = saveGame(connection, janggiGame);
            savePieces(connection, gameId, janggiGame);
            connection.commit();
            return gameId;
        } catch (SQLException exception) {
            throw new IllegalStateException("새 게임 저장에 실패했습니다.", exception);
        }
    }

    @Override
    public Optional<SavedGame> findPlayingGame() {
        return Optional.empty();
    }

    @Override
    public void updateAfterMove(long gameId, JanggiGame game, Position from, Position to) {

    }

    private long saveGame(Connection connection, JanggiGame janggiGame) throws SQLException {
        String sql = "INSERT INTO games(turn, status) VALUES(?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, janggiGame.currentTurnTeam().name());
            statement.setString(2, toStatus(janggiGame));
            statement.executeUpdate();
            return generatedGameId(statement);
        }
    }

    private void savePieces(Connection connection, long gameId, JanggiGame janggiGame) throws SQLException {
        String sql = "INSERT INTO pieces(game_id, x, y, name, team) VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Map.Entry<Position, Piece> entry : janggiGame.board().getBoard().entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();
                statement.setLong(1, gameId);
                statement.setInt(2, position.x());
                statement.setInt(3, position.y());
                statement.setString(4, piece.getName().name());
                statement.setString(5, piece.getTeam().name());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private long generatedGameId(PreparedStatement statement) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (!generatedKeys.next()) {
                throw new IllegalStateException("저장된 게임 id를 찾을 수 없습니다.");
            }
            return generatedKeys.getLong(1);
        }
    }

    private String toStatus(JanggiGame janggiGame) {
        if (janggiGame.isPlaying()) {
            return "PLAYING";
        }
        return "FINISHED";
    }
}
