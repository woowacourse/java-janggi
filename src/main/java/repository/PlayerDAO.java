package repository;

import domain.player.Player;
import domain.player.Score;
import domain.player.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class PlayerDAO {
    private final Connector connector;
    private final AtomicInteger counter = new AtomicInteger(0);

    public PlayerDAO(final Connector connector) {
        this.connector = connector;
    }

    public void createWithRoomId(final Player player, final int roomId) {
        final String query = "INSERT INTO player(team,score,is_turn,room_id) VALUES(?, ?, ?, ?)";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, player.getTeam().name());
            preparedStatement.setDouble(2, player.getScore().value());
            preparedStatement.setBoolean(3, player.isTurn());
            preparedStatement.setInt(4, roomId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(final Player player) {
        final String query = "UPDATE player SET score = ?, is_turn = ? WHERE id = ?";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, player.getScore().value());
            preparedStatement.setBoolean(2, player.isTurn());
            preparedStatement.setInt(3, player.getId());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Player findById(final int id) {
        final String query = "SELECT * FROM player WHERE id = ?";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            final ResultSet resultSet = preparedStatement.executeQuery();
            return convertResultSetToPlayer(resultSet);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getNextId() {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(id) AS last_id FROM player");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return incrementLastId(resultSet);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Player> findAllByRoomId(final int roomId) {
        final String query = "SELECT * FROM player WHERE room_id = ?";
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, roomId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return convertResultSetToPlayers(resultSet);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int incrementLastId(final ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return counter.addAndGet(resultSet.getInt("last_id"));
        }
        return counter.get();
    }

    private List<Player> convertResultSetToPlayers(ResultSet resultSet) throws SQLException {
        final List<Player> players = new ArrayList<>();
        while (resultSet.next()) {
            Player player = convertResultSetToPlayer(resultSet);
            players.add(player);
        }
        return players;
    }

    private Player convertResultSetToPlayer(final ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new Player(
                    resultSet.getInt("id"),
                    Team.valueOf(resultSet.getString("team")),
                    new Score(resultSet.getDouble("score")),
                    resultSet.getBoolean("is_turn")
            );
        }
        return null;
    }
}
