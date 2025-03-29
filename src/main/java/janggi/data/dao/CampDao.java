package janggi.data.dao;

import janggi.data.DatabaseConnection;
import janggi.piece.Camp;
import java.sql.SQLException;

public final class CampDao {

    public void saveAll(Camp... camps) {
        for (Camp camp : camps) {
            save(camp);
        }
    }

    public void save(Camp camp) {
        final String query = """
                INSERT IGNORE INTO camp (name)
                       VALUES (?)
                """;
        try (final var connection = DatabaseConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, camp.name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findIdByName(String name) {
        final String query = """
                SELECT *
                FROM camp
                WHERE name = ?
                """;
        try (final var connection = DatabaseConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
            throw new IllegalArgumentException("해당 이름의 진영이 존재하지 않습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
