package infra;

import domain.janggigame.JanggiGame;
import repository.GameRepository;

import java.sql.*;
import java.util.Optional;

public class JDBCGameRepository implements GameRepository {
    @Override
    public Long save(JanggiGame janggiGame) {
        String insertSql = """
                INSERT INTO game (current_turn, is_finished)
                VALUES (?, ?)
                """;

        try (Connection connection = DriverManager.getConnection(JDBCContext.URL, JDBCContext.USER, JDBCContext.PASSWORD)) {
            PreparedStatement insertStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

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
    public void update(Long gameId, JanggiGame janggiGame) {
        String sql = "UPDATE game SET current_turn = ?, is_finished = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(JDBCContext.URL, JDBCContext.USER, JDBCContext.PASSWORD);
        PreparedStatement statement = connection.prepareStatement(sql)) {

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
        return false;
    }

    @Override
    public Optional<JanggiGame> findById(Long gameId) {
        return Optional.empty();
    }

    @Override
    public String findCurrentTurnById(Long gameId) {
        return "";
    }
}
