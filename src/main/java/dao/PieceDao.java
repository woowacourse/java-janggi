package dao;

import entity.PieceEntity;
import execptions.JanggiArgumentException;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public PieceEntity findById(final long findId) {
        final var query = "SELECT * FROM piece WHERE id = ?";

        return executeQuery(
                query,
                preparedStatement -> preparedStatement.setLong(1, findId),
                resultSet -> {
                    if (resultSet.next()) {
                        long id = resultSet.getLong("id");
                        long teamId = resultSet.getLong("team_id");
                        String type = resultSet.getString("type");

                        return new PieceEntity(id, teamId, type);
                    }

                    throw new JanggiArgumentException("해당 조건에 맞는 기물이 존재하지 않습니다.");
                }
        );
    }
}
