package domain.dao;

import static util.DBConnectionUtil.close;
import static util.DBConnectionUtil.getConnection;

import domain.GameStatus;
import domain.piece.TeamType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class GameStatusDao {

    public void save(GameStatus gameStatus) {
        String sql = "insert into game(room_name,turn) values(?,?)";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, gameStatus.getRoomName());
            preparedStatement.setString(2, gameStatus.getTurn().name());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection, preparedStatement, null);
        }
    }

    public Optional<GameStatus> findGameStatusByRoomName(String roomName) {
        String sql = "select * from game where room_name = ?";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, roomName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new GameStatus(resultSet.getString("room_name"),
                        TeamType.valueOf(resultSet.getString("turn"))));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }
    }

    public void updateTurn(String roomName, TeamType turn) {
        Connection connection = getConnection();
        safeModeQuit(connection);
        updateGameStatus(connection, roomName, turn);
        safeModeSet(connection);
        close(connection, null, null);
    }

    public void deleteGame() {
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
        String sql = "delete from game";
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

    private void updateGameStatus(Connection connection, String roomName, TeamType turn) {
        String sql = "update game set turn = ? where room_name = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, turn.name());
            preparedStatement.setString(2, roomName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(null, preparedStatement, null);
        }
    }

    private void safeModeQuit(Connection connection) {
        String safeModeQuit = "SET SQL_SAFE_UPDATES = 0";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(safeModeQuit);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(null, preparedStatement, null);
        }
    }

    private void safeModeSet(Connection connection) {
        String safeModeSet = "SET SQL_SAFE_UPDATES = 1";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(safeModeSet);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(null, preparedStatement, null);
        }
    }
}
