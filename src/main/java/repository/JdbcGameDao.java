package repository;

import domain.game.JanggiGame;
import domain.state.GameState;
import domain.state.GameStateFactory;
import dto.JanggiGameDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcGameDao {

    private static final String ID = "id";
    private static final String CURRENT_TEAM = "current_team";
    private static final String WINNER = "winner";

    public Long save(final Connection connection, final JanggiGame janggiGame) throws SQLException {
        String sql = "INSERT INTO game (current_team, winner) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, getCurrentTeam(janggiGame));
            preparedStatement.setString(2, getWinner(janggiGame));
            preparedStatement.executeUpdate();

            return generatedId(preparedStatement);
        }
    }

    private String getCurrentTeam(final JanggiGame janggiGame) {
        if (janggiGame.isFinished()) {
            return null;
        }
        return janggiGame.getCurrentTeam().toString();
    }

    public List<JanggiGameDto> findAll(final Connection connection) throws SQLException {
        String sql = "SELECT id, current_team, winner FROM game ORDER BY id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return mapToDtos(resultSet);
        }
    }

    public Optional<GameState> findGameState(final Connection connection, final Long gameId) throws SQLException {
        String sql = "SELECT current_team, winner FROM game WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, gameId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapToGameState(resultSet));
                }
                return Optional.empty();
            }
        }
    }

    public void update(final Connection connection, final Long gameId, final JanggiGame janggiGame)
            throws SQLException {
        String sql = "UPDATE game SET current_team = ?, winner = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, getCurrentTeam(janggiGame));
            preparedStatement.setString(2, getWinner(janggiGame));
            preparedStatement.setLong(3, gameId);
            preparedStatement.executeUpdate();
        }
    }

    private String getWinner(final JanggiGame janggiGame) {
        if (janggiGame.isFinished()) {
            return janggiGame.getWinnerTeam().name();
        }
        return null;
    }

    private Long generatedId(final PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            throw new SQLException("ID 생성에 실패했습니다.");
        }
    }

    private List<JanggiGameDto> mapToDtos(final ResultSet resultSet) throws SQLException {
        List<JanggiGameDto> games = new ArrayList<>();
        while (resultSet.next()) {

            games.add(new JanggiGameDto(
                    resultSet.getLong(ID),
                    resultSet.getString(CURRENT_TEAM),
                    resultSet.getString(WINNER)
            ));
        }
        return games;
    }

    private GameState mapToGameState(final ResultSet resultSet) throws SQLException {
        final String winner = resultSet.getString(WINNER);
        final String currentTeam = resultSet.getString(CURRENT_TEAM);

        return GameStateFactory.from(winner, currentTeam);
    }
}
