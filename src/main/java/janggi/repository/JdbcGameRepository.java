package janggi.repository;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import janggi.config.DatabaseManager;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.CurrentTurn;
import janggi.domain.game.Game;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcGameRepository implements GameRepository {

    @Override
    public Long save(Game game) {
        String gameSql = "INSERT INTO janggi_game (turn) VALUES (?)";

        try (Connection connection = DatabaseManager.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement gameStatement = connection.prepareStatement(gameSql, RETURN_GENERATED_KEYS)) {
                gameStatement.setString(1, game.currentDynasty().name());
                gameStatement.executeUpdate();

                ResultSet keys = gameStatement.getGeneratedKeys();
                if (!keys.next()) {
                    throw new RuntimeException("id 생성 실패");
                }
                connection.commit();
                return keys.getLong(1);
            } catch (Exception e) {
                connection.rollback();
                throw new RuntimeException("저장 실패", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("저장 실패", e);
        }
    }

    @Override
    public Optional<CurrentTurn> findByCurrentTurnById(Long gameId) {
        String gameSql = "SELECT turn FROM janggi_game WHERE id = ?";

        try (Connection connection = DatabaseManager.getConnection()) {
            Dynasty currentDynasty = findCurrentTurn(connection, gameSql, gameId);
            return Optional.of(new CurrentTurn(currentDynasty));
        } catch (SQLException e) {
            throw new RuntimeException("조회 실패", e);
        }
    }

    @Override
    public void updateTurn(Long gameId, Dynasty currentTurn) {
        String updateGameSql = "UPDATE janggi_game SET turn = ? WHERE id = ?";

        try (Connection connection = DatabaseManager.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement updateGameStatement = connection.prepareStatement(updateGameSql)) {
                updateGameStatement.setString(1, currentTurn.name());
                updateGameStatement.setLong(2, gameId);
                updateGameStatement.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("수정 실패", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("수정 실패", e);
        }
    }

    private Dynasty findCurrentTurn(Connection connection, String sql, Long gameId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }
                return Dynasty.valueOf(resultSet.getString("turn"));
            }
        }
    }

}
