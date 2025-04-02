package janggi.data.dao.mysql;

import janggi.data.DatabaseConnection;
import janggi.data.dao.CampDao;
import janggi.data.exception.DatabaseQueryException;
import janggi.piece.Camp;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class MySqlCampDao implements CampDao {

    @Override
    public void saveAll(Camp... camps) {
        for (Camp camp : camps) {
            save(camp);
        }
    }

    private void save(Camp camp) {
        final String query = """
                INSERT IGNORE INTO camp (name)
                VALUES (?)
                """;
        try (final var connection = DatabaseConnection.createConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, camp.name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new DatabaseQueryException("진영 정보를 저장하는 도중 오류가 발생했습니다. %s".formatted(e.getMessage()));
        }
    }

    @Override
    public int findIdByName(String name) {
        final String query = """
                SELECT id
                FROM camp
                WHERE name = ?
                """;
        try (final var connection = DatabaseConnection.createConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            final var resultSet = preparedStatement.executeQuery();
            return getIdIfExist(resultSet);
        } catch (final SQLException e) {
            throw new DatabaseQueryException("진영 정보를 조회하는 도중 오류가 발생했습니다. %s".formatted(e.getMessage()));
        }
    }

    private int getIdIfExist(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        throw new IllegalArgumentException("해당 이름의 진영이 존재하지 않습니다.");
    }
}
