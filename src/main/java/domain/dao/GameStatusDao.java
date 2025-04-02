package domain.dao;

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
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, gameStatus.getRoomName());
            preparedStatement.setString(2, gameStatus.getTurn().name());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<GameStatus> findGameStatusByRoomName(String roomName) {
        String sql = "select * from game where room_name = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, roomName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new GameStatus(resultSet.getString("room_name"),
                            TeamType.valueOf(resultSet.getString("turn"))));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTurn(String roomName, TeamType turn) {
        try (Connection connection = getConnection()) {
            safeModeQuit(connection);
            updateGameStatus(connection, roomName, turn);
            safeModeSet(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteGame() {
        try (Connection connection = getConnection()) {
            try {
                connection.setAutoCommit(false);
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
        String sql = "delete from game";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateGameStatus(Connection connection, String roomName, TeamType turn) {
        String sql = "update game set turn = ? where room_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, turn.name());
            preparedStatement.setString(2, roomName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void safeModeQuit(Connection connection) {
        String sql = "SET SQL_SAFE_UPDATES = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, 0);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void safeModeSet(Connection connection) {
        String sql = "SET SQL_SAFE_UPDATES = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
