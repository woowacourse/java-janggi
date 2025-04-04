package dao;

import entity.PieceEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public final class PieceDao {

    private final JanggiConnection janggiConnection;

    public PieceDao(JanggiConnection janggiConnection) {
        this.janggiConnection = janggiConnection;
    }

    private <T> T executeQuery(String sql, StatementSetter setter, ResultSetExtractor<T> extractor) {
        try (final var connection = janggiConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(sql)) {

            setter.setValues(preparedStatement);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                return extractor.extractData(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<PieceEntity> findById(final long findId) {
        final var query = "SELECT * FROM piece WHERE id = ?";

        return Optional.ofNullable(executeQuery(
                query,
                preparedStatement -> preparedStatement.setLong(1, findId),
                resultSet -> {
                    if (resultSet.next()) {
                        long id = resultSet.getLong("id");
                        long teamId = resultSet.getLong("team_id");
                        String type = resultSet.getString("type");

                        return new PieceEntity(id, teamId, type);
                    }
                    return null;
                }
        ));
    }
}
