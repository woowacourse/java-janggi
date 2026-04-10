package infra;

import domain.janggigame.JanggiGame;
import domain.piece.Side;
import repository.GameRepository;

import java.sql.*;
import java.util.Optional;

public class JDBCGameRepository implements GameRepository {
    @Override
    public Long save(Connection connection, JanggiGame janggiGame) {
        String insertSql = """
                INSERT INTO game (current_turn, is_finished)
                VALUES (?, ?)
                """;

        try (PreparedStatement insertStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {

            insertStatement.setString(1, janggiGame.getWhoseTurn().name());
            insertStatement.setBoolean(2, janggiGame.isFinished());

            insertStatement.executeUpdate();

            try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1); // 방금 발급된 ID 반환!
                } else {
                    throw new SQLException("ID 생성 실패");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("DB 오류", e);
        }
    }

    @Override
    public void update(Connection connection, Long gameId, JanggiGame janggiGame) {
        String updateSql = "UPDATE game SET current_turn = ?, is_finished = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(updateSql)) {

            statement.setString(1, janggiGame.getWhoseTurn().name());
            statement.setBoolean(2, janggiGame.isFinished());
            statement.setLong(3, gameId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("게임 업데이트 실패", e);
        }
    }

    @Override
    public boolean isFinished(Long gameId) {
        String sql = "SELECT is_finished FROM game WHERE id = ?";

        try (Connection connection = JDBCContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getBoolean("is_finished");
        } catch (SQLException e) {
            throw new RuntimeException("조회 실패", e);
        }
    }

    @Override
    public Optional<Side> findCurrentTurnById(Long gameId) {
        String sql = "SELECT current_turn FROM game WHERE id = ?";

        try (Connection connection = JDBCContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Side side = Side.valueOf(resultSet.getString("current_turn"));
            return Optional.of(side);
        } catch (SQLException e) {
            throw new RuntimeException("조회 실패", e);
        }
    }
}
