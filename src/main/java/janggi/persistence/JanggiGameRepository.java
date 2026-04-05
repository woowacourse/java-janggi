package janggi.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JanggiGameRepository implements GameRepository {

    @Override
    public Optional<Long> findActiveGameId(Connection connection) throws SQLException {
        String sql = "select game_id from game where is_finished = FALSE order by created_at DESC LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return extractActiveGameId(statement);
        }
    }

    private Optional<Long> extractActiveGameId(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            return mapToGameIdOptional(resultSet);
        }
    }

    private Optional<Long> mapToGameIdOptional(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return Optional.empty();
        }
        return Optional.of(resultSet.getLong("game_id"));
    }
}
