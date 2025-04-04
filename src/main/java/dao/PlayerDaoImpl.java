package dao;

import dao.connect.ConnectionProvider;
import domain.Player;
import domain.Team;
import domain.piece.Score;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDaoImpl implements PlayerDao {

    private final ConnectionProvider connectionProvider;

    public PlayerDaoImpl(final ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public void save(final String gameName, final Player player) {
        final String query = "INSERT INTO player (team, game_name, score) VALUES (?, ?, ?)";
        try (final Connection connection = connectionProvider.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, player.getTeam().name());
            preparedStatement.setString(2, gameName);
            preparedStatement.setInt(3, player.getScore().value());
            preparedStatement.executeUpdate();
            connectionProvider.closeConnection(connection);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Player> findAllByGameName(final String gameName) {
        final String query = "SELECT team, game_name, score FROM player WHERE game_name = ?";
        try (final Connection connection = connectionProvider.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameName);
            final var resultSet = preparedStatement.executeQuery();
            connectionProvider.closeConnection(connection);
            return mapResultSetToPlayers(resultSet);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Player> mapResultSetToPlayers(final ResultSet result) {
        try {
            final List<Player> players = new ArrayList<>();
            while (result.next()) {
                final String team = result.getString("team");
                final int score = result.getInt("score");
                players.add(new Player(Team.valueOf(team), new Score(score)));
            }
            return players;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
