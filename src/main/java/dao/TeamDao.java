package dao;

import entity.TeamEntity;
import execptions.JanggiArgumentException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamDao {
    private final JanggiConnection janggiConnection;

    public TeamDao(JanggiConnection janggiConnection) {
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

    public TeamEntity findById(long teamId) {
        final var query = "SELECT * FROM team WHERE id = ?";

        return executeQuery(
                query,
                preparedStatement -> preparedStatement.setLong(1, teamId),
                resultSet -> {
                    if (resultSet.next()) {
                        return new TeamEntity(resultSet.getLong("id"), resultSet.getString("name"));
                    }
                    return null;
                }
        );
    }

    public TeamEntity findByName(String comparedNamed) {
        final var query = "SELECT * FROM team WHERE name = ?";

        return executeQuery(
                query,
                preparedStatement -> preparedStatement.setString(1, comparedNamed),
                resultSet -> {
                    if (resultSet.next()) {
                        long id = resultSet.getLong("id");
                        String name = resultSet.getString("name");

                        return new TeamEntity(id, name);
                    }
                    throw new JanggiArgumentException("해당 이름을 갖는 팀이 존재하지 않습니다.");
                });
    }
}
