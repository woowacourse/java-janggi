package janggi.dao;

import janggi.domain.GameStatus;
import janggi.entity.JanggiEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class JdbcJanggiDao extends AbstractJdbcDao implements JanggiDao {

    @Override
    public JanggiEntity save(final JanggiEntity janggiEntity) {
        Optional<Long> janggiIdOptional = findJanggiIdByRedAndGreenPlayerNameAndGameStatus(janggiEntity.redPlayerName(),
                janggiEntity.greenPlayerName(),
                GameStatus.CONTINUE.name());
        if (janggiIdOptional.isPresent()) {
            update(janggiEntity.addJanggiId(janggiIdOptional.get()));
            return janggiEntity;
        }
        insert(janggiEntity);
        return findByRedAndGreenPlayerNameAndGameStatus(janggiEntity.redPlayerName(),
                janggiEntity.greenPlayerName(),
                janggiEntity.gameStatus())
                .orElseThrow(() -> new IllegalStateException("insert 오류: " + janggiEntity));
    }

    private void insert(final JanggiEntity janggiEntity) {
        final String query = """
                INSERT INTO janggi(red_player_name,
                                   green_player_Name,
                                   red_score,
                                   green_score,
                                   game_status,
                                   game_turn)
                VALUES (?, ?, ?, ?, ?, ?)""";

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, janggiEntity.redPlayerName());
            preparedStatement.setString(2, janggiEntity.greenPlayerName());
            preparedStatement.setDouble(3, janggiEntity.redScore());
            preparedStatement.setDouble(4, janggiEntity.greenScore());
            preparedStatement.setString(5, janggiEntity.gameStatus());
            preparedStatement.setString(6, janggiEntity.gameTurn());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException("DB 처리에 문제가 발생했습니다", e);
        }
    }

    private void update(final JanggiEntity janggiEntity) {
        final String query = """
                UPDATE janggi
                SET red_score = ?, green_score = ?, game_status = ?, game_turn = ? 
                WHERE janggi_id = ?""";

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, janggiEntity.redScore());
            preparedStatement.setDouble(2, janggiEntity.greenScore());
            preparedStatement.setString(3, janggiEntity.gameStatus());
            preparedStatement.setString(4, janggiEntity.gameTurn());

            preparedStatement.setLong(5, janggiEntity.janggiId());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException("DB 처리에 문제가 발생했습니다", e);
        }
    }

    @Override
    public Optional<JanggiEntity> findByRedAndGreenPlayerNameAndGameStatus(final String redPlayerName,
                                                                           final String greenPlayerName,
                                                                           final String gameStatus) {
        final String query = """
                SELECT * 
                FROM janggi
                where red_player_name = ? AND green_player_name = ? AND game_status = ?""";

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, redPlayerName);
            preparedStatement.setString(2, greenPlayerName);
            preparedStatement.setString(3, gameStatus);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new JanggiEntity(resultSet.getLong("janggi_id"),
                        resultSet.getString("red_player_name"),
                        resultSet.getString("green_player_name"),
                        resultSet.getDouble("red_score"),
                        resultSet.getDouble("green_score"),
                        resultSet.getString("game_status"),
                        resultSet.getString("game_turn")));
            }
            return Optional.empty();
        } catch (final SQLException e) {
            throw new IllegalStateException("DB 처리에 문제가 발생했습니다", e);
        }
    }

    @Override
    public boolean existsByRedAndGreenPlayerNameAndGameStatus(final String redPlayerName,
                                                              final String greenPlayerName,
                                                              final String gameStatus) {
        return findByRedAndGreenPlayerNameAndGameStatus(redPlayerName, greenPlayerName, gameStatus)
                .isPresent();
    }

    @Override
    public Optional<Long> findJanggiIdByRedAndGreenPlayerNameAndGameStatus(final String redPlayerName,
                                                                           final String greenPlayerName,
                                                                           final String targetStatus) {
        final String query = """
                SELECT janggi_id 
                FROM janggi
                where red_player_name = ? AND green_player_name = ? AND game_status = ?""";

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, redPlayerName);
            preparedStatement.setString(2, greenPlayerName);
            preparedStatement.setString(3, targetStatus);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getLong("janggi_id"));
            }
            return Optional.empty();
        } catch (final SQLException e) {
            throw new IllegalStateException("DB 처리에 문제가 발생했습니다", e);
        }
    }
}
