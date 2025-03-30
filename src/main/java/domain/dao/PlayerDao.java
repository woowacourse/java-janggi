package domain.dao;

import static util.DBConnectionUtil.close;
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
        Connection connection = getConnection();
        String sql = "insert into player(username,team) values (?,?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, player.getName());
            preparedStatement.setString(2, player.getTeamType().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection, preparedStatement, null);
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
        Connection connection = getConnection();
        String sql = "select * from player where team = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, teamType.name());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Player(resultSet.getString("username"), TeamType.valueOf(resultSet.getString("team")));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }
    }

    public void deletePlayer() {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
            safeModeQuit(connection);
            deleteAll(connection);
            safeModeSet(connection);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        close(connection, null, null);
    }

    private void deleteAll(Connection connection) {
        String sql = "delete from player";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(null, preparedStatement, null);
        }
    }

    private void safeModeQuit(Connection connection) {
        String safeModeQuit = "SET SQL_SAFE_UPDATES = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(safeModeQuit);
            preparedStatement.setInt(1,0);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(null, preparedStatement, null);
        }
    }

    private void safeModeSet(Connection connection) {
        String safeModeSet = "SET SQL_SAFE_UPDATES = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(safeModeSet);
            preparedStatement.setInt(1,1);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(null, preparedStatement, null);
        }
    }

}
