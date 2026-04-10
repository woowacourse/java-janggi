package persistence;

import domain.Position;
import domain.Side;
import domain.board.Formation;
import domain.piece.Piece;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcGameRepository {
    private static final String STATUS_IN_PROGRESS = "IN_PROGRESS";
    private static final String STATUS_FINISHED = "FINISHED";

    private final ConnectionFactory connectionFactory;
    private final BoardSnapshotConverter boardSnapshotConverter;

    public JdbcGameRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        this.boardSnapshotConverter = new BoardSnapshotConverter();
    }

    public long createGame(
            String choPlayerName,
            String hanPlayerName,
            Formation choFormation,
            Formation hanFormation,
            Map<Position, Piece> board,
            Side currentSide,
            int moveCount
    ) {
        String sql = """
                INSERT INTO game(
                    cho_player_name, han_player_name, cho_formation, han_formation,
                    board_state, current_side, move_count, status
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, choPlayerName);
            preparedStatement.setString(2, hanPlayerName);
            preparedStatement.setString(3, choFormation.name());
            preparedStatement.setString(4, hanFormation.name());
            preparedStatement.setString(5, boardSnapshotConverter.serialize(board));
            preparedStatement.setString(6, currentSide.name());
            preparedStatement.setInt(7, moveCount);
            preparedStatement.setString(8, STATUS_IN_PROGRESS);
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new RuntimeException("게임 ID를 생성하지 못했습니다.");
                }
                return generatedKeys.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateGameState(long gameId, Map<Position, Piece> board, Side currentSide, int moveCount) {
        String updateGameSql = """
                UPDATE game
                SET board_state = ?, current_side = ?, move_count = ?, updated_at = CURRENT_TIMESTAMP
                WHERE id = ?
                """;

        try (Connection connection = connectionFactory.getConnection()) {
            try (PreparedStatement updateGame = connection.prepareStatement(updateGameSql)) {
                updateGame.setString(1, boardSnapshotConverter.serialize(board));
                updateGame.setString(2, currentSide.name());
                updateGame.setInt(3, moveCount);
                updateGame.setLong(4, gameId);
                updateGame.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SavedGameSummary> findInProgressGames() {
        String sql = """
                SELECT id, cho_player_name, han_player_name, move_count
                FROM game
                WHERE status = ?
                ORDER BY updated_at DESC, id DESC
                """;

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, STATUS_IN_PROGRESS);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<SavedGameSummary> savedGames = new ArrayList<>();
                while (resultSet.next()) {
                    savedGames.add(new SavedGameSummary(
                            resultSet.getLong("id"),
                            resultSet.getString("cho_player_name"),
                            resultSet.getString("han_player_name"),
                            resultSet.getInt("move_count")
                    ));
                }
                return savedGames;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<SavedGame> findInProgressById(long gameId) {
        String gameSql = """
                SELECT id, cho_player_name, han_player_name, cho_formation, han_formation,
                       board_state, current_side, move_count
                FROM game
                WHERE status = ? AND id = ?
                """;

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(gameSql)) {
            preparedStatement.setString(1, STATUS_IN_PROGRESS);
            preparedStatement.setLong(2, gameId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                long foundGameId = resultSet.getLong("id");
                String choPlayerName = resultSet.getString("cho_player_name");
                String hanPlayerName = resultSet.getString("han_player_name");
                Formation choFormation = Formation.valueOf(resultSet.getString("cho_formation"));
                Formation hanFormation = Formation.valueOf(resultSet.getString("han_formation"));
                String boardState = resultSet.getString("board_state");
                Side currentSide = Side.valueOf(resultSet.getString("current_side"));
                int moveCount = resultSet.getInt("move_count");

                return Optional.of(new SavedGame(
                        foundGameId,
                        choPlayerName,
                        hanPlayerName,
                        choFormation,
                        hanFormation,
                        boardSnapshotConverter.deserialize(boardState),
                        currentSide,
                        moveCount
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void finishGame(long gameId) {
        String sql = """
                UPDATE game
                SET status = ?, updated_at = CURRENT_TIMESTAMP
                WHERE id = ?
                """;

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, STATUS_FINISHED);
            preparedStatement.setLong(2, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
