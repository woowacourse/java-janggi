package persistence;

import domain.Position;
import domain.board.Formation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcGameRepository {
    private static final String STATUS_IN_PROGRESS = "IN_PROGRESS";
    private static final String STATUS_FINISHED = "FINISHED";

    private final ConnectionFactory connectionFactory;

    public JdbcGameRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public long createGame(String choPlayerName, String hanPlayerName, Formation choFormation, Formation hanFormation) {
        String sql = """
                INSERT INTO game(cho_player_name, han_player_name, cho_formation, han_formation, status)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, choPlayerName);
            preparedStatement.setString(2, hanPlayerName);
            preparedStatement.setString(3, choFormation.name());
            preparedStatement.setString(4, hanFormation.name());
            preparedStatement.setString(5, STATUS_IN_PROGRESS);
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

    public void saveMove(long gameId, int turnNo, Position source, Position target) {
        String insertMoveSql = """
                INSERT INTO move(game_id, turn_no, source_x, source_y, target_x, target_y)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        String updateGameSql = "UPDATE game SET updated_at = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection connection = connectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement insertMove = connection.prepareStatement(insertMoveSql);
                 PreparedStatement updateGame = connection.prepareStatement(updateGameSql)) {
                List<Integer> sourcePosition = source.getPosition();
                List<Integer> targetPosition = target.getPosition();

                insertMove.setLong(1, gameId);
                insertMove.setInt(2, turnNo);
                insertMove.setInt(3, sourcePosition.getFirst());
                insertMove.setInt(4, sourcePosition.getLast());
                insertMove.setInt(5, targetPosition.getFirst());
                insertMove.setInt(6, targetPosition.getLast());
                insertMove.executeUpdate();

                updateGame.setLong(1, gameId);
                updateGame.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    e.addSuppressed(rollbackException);
                }
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SavedGameSummary> findInProgressGames() {
        String sql = """
                SELECT g.id, g.cho_player_name, g.han_player_name, COUNT(m.id) AS move_count
                FROM game g
                LEFT JOIN move m ON g.id = m.game_id
                WHERE g.status = ?
                GROUP BY g.id, g.cho_player_name, g.han_player_name, g.updated_at
                ORDER BY g.updated_at DESC, g.id DESC
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
                SELECT id, cho_player_name, han_player_name, cho_formation, han_formation
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
                List<MoveCommand> moves = findMoves(connection, foundGameId);

                return Optional.of(new SavedGame(
                        foundGameId,
                        choPlayerName,
                        hanPlayerName,
                        choFormation,
                        hanFormation,
                        moves
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

    private List<MoveCommand> findMoves(Connection connection, long gameId) throws SQLException {
        String sql = """
                SELECT source_x, source_y, target_x, target_y
                FROM move
                WHERE game_id = ?
                ORDER BY turn_no ASC
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, gameId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<MoveCommand> moves = new ArrayList<>();
                while (resultSet.next()) {
                    Position source = Position.of(
                            resultSet.getInt("source_x"),
                            resultSet.getInt("source_y")
                    );
                    Position target = Position.of(
                            resultSet.getInt("target_x"),
                            resultSet.getInt("target_y")
                    );
                    moves.add(new MoveCommand(source, target));
                }
                return moves;
            }
        }
    }

}
