package repository;

import domain.Side;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameRepository {

    public boolean doesGameExist() {
        String sql = "SELECT EXISTS (SELECT 1 FROM GAME)";
        try (Connection connection = Database.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getBoolean(1);
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Side getCurrentTurn() {
        String sql = "SELECT turn FROM GAME LIMIT 1";
        try (Connection connection = Database.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery()) {
            if (resultSet.next()) {
                return Side.valueOf(resultSet.getString("turn"));
            }
            throw new RuntimeException("게임이 존재하지 않습니다.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createGame(Connection connection, Side turn) throws SQLException {
        String sql = "INSERT INTO GAME (turn) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, turn.name());
            pstmt.executeUpdate();
        }
    }

    public void updateCurrentTurn(Connection connection, Side turn) throws SQLException {
        String sql = "UPDATE GAME SET turn = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, turn.name());
            pstmt.executeUpdate();
        }
    }

    public void resetAll(Connection connection) throws SQLException {
        String sql = "TRUNCATE TABLE GAME";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.executeUpdate();
        }

    }
}
