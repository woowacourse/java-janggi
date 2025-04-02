package domain.dao;

import static util.DBConnectionUtil.getConnection;

import domain.participants.Player;
import domain.participants.Players;
import domain.piece.TeamType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PlayerDao {

    public void save(Player player) {
        String sql = "insert into player(username,team) values (?,?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, player.getName());
            preparedStatement.setString(2, player.getTeamType().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void savePlayers(Players players) {
        save(players.getChoPlayer());
        save(players.getHanPlayer());
    }

    public Optional<Players> findPlayers() {
        Player choPlayer = findPlayer(TeamType.CHO);
        Player hanPlayer = findPlayer(TeamType.HAN);
        if (choPlayer != null && hanPlayer != null) {
            return Optional.of(Players.initialize(choPlayer, hanPlayer));
        }
        return Optional.empty();
    }

    private Player findPlayer(TeamType teamType) {
        String sql = "select * from player where team = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, teamType.name());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Player(resultSet.getString("username"), TeamType.valueOf(resultSet.getString("team")));
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePlayer() {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                safeModeQuit(connection);
                deleteAll(connection);
                safeModeSet(connection);
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                throw new RuntimeException(ex);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteAll(Connection connection) {
        String sql = "delete from player";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void safeModeQuit(Connection connection) {
        String safeModeQuit = "SET SQL_SAFE_UPDATES = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(safeModeQuit)) {
            preparedStatement.setInt(1, 0);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void safeModeSet(Connection connection) {
        String safeModeSet = "SET SQL_SAFE_UPDATES = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(safeModeSet)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
