package dao;

import dto.SwitchPlayerTurnRequestDto;
import entity.PieceEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public final class PieceDao {

    private final JanggiConnection janggiConnection;

    public PieceDao(JanggiConnection janggiConnection) {
        this.janggiConnection = janggiConnection;
    }

    public PieceEntity findById(final long findId) {
        final var query = "SELECT * FROM piece WHERE id = ?";

        try (final var connection = janggiConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, findId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                long teamId = resultSet.getLong("team_id");
                String type = resultSet.getString("type");

                return new PieceEntity(id, teamId, type);
            }

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
