package dao;

import entity.TeamEntity;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamDao {
    private final JanggiConnection janggiConnection;

    public TeamDao(JanggiConnection janggiConnection) {
        this.janggiConnection = janggiConnection;
    }

    public TeamEntity findByid(long teamId) {
        final var query = "SELECT * FROM team WHERE id = ?";

        try (final var connection = janggiConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, teamId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");

                return new TeamEntity(id, name);
            }

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
