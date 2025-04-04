package dao;

import dto.SwitchPlayerTurnRequestDto;
import entity.PlayerEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDao {
    private final JanggiConnection janggiConnection;

    public PlayerDao(JanggiConnection janggiConnection) {
        this.janggiConnection = janggiConnection;
    }

    public <T> List<T> executeQueryForList(String sql, StatementSetter setter, RowMapper<T> rowMapper) {
        try (var connection = janggiConnection.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            setter.setValues(preparedStatement);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<T> results = new ArrayList<>();
                while (resultSet.next()) {
                    results.add(rowMapper.mapRow(resultSet));
                }
                return results;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeUpdate(String sql, StatementSetter setter) {
        try (final var connection = janggiConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(sql)) {

            setter.setValues(preparedStatement);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PlayerEntity> getAllPlayers() {
        final var query = "SELECT * FROM player";

        return executeQueryForList(
                query,
                preparedStatement -> {
                },
                resultSet -> new PlayerEntity(
                        resultSet.getLong("id"),
                        resultSet.getLong("team_id")
                )
        );
    }

    public void saveSwitchedTurn(final List<SwitchPlayerTurnRequestDto> requestDtos) {
        final var query = "UPDATE player SET is_turn = ? WHERE team_id = ?";

        for (final SwitchPlayerTurnRequestDto requestDto : requestDtos) {
            executeUpdate(
                    query,
                    preparedStatement -> {
                        preparedStatement.setBoolean(1, requestDto.isTurn());
                        preparedStatement.setLong(2, requestDto.teamId());

                    }
            );
        }
    }

}
