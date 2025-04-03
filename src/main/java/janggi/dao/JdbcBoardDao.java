package janggi.dao;

import janggi.entity.BoardEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class JdbcBoardDao extends AbstractJdbcDao implements BoardDao {
    @Override
    public void save(final BoardEntity boardEntity) {
        final String query = """
                INSERT INTO board(janggi_id)
                VALUES (?)""";

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, boardEntity.janggiId());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException("DB 처리에 문제가 발생했습니다", e);
        }
    }

    @Override
    public Optional<Long> findByJanggiId(final long janggiId) {
        final String query = """
                SELECT * 
                FROM board
                where janggi_id = ?
                """;

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, janggiId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(resultSet.getLong("board_id"));
            }
            return Optional.empty();
        } catch (final SQLException e) {
            throw new IllegalStateException("DB 처리에 문제가 발생했습니다", e);
        }
    }
}
