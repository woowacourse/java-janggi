package janggi.dao;

import janggi.camp.Camp;
import janggi.infra.DatabaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDao {

    public void initializeGame(Camp firstTurnCamp) {
        String query = "INSERT INTO game (is_end, turn) VALUES (?, ?)";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBoolean(1, false);
            preparedStatement.setString(2, firstTurnCamp.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("보드 초기화 실패", e);
        }
    }

    public Camp findLatestTurn() {
        String query = "SELECT * FROM game WHERE id = ?";
        int gameId = findActiveGameId();
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Camp.from(resultSet.getString("turn"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("현재 턴 조회 실패", e);
        }
        return null;
    }

    public int findActiveGameId() {
        String query = "SELECT * FROM game WHERE is_end = false";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("진행 중인 보드 조회 실패", e);
        }
        return 0;
    }

    public void endGame() {
        String query = "UPDATE game SET is_end = ? WHERE id= ?";
        int gameId = findActiveGameId();
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("게임 종료 처리 실패", e);
        }
    }

    public void updateTurn(Camp camp) {
        String query = "UPDATE game SET turn = ? WHERE id= ?";
        int gameId = findActiveGameId();
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, camp.getName());
            preparedStatement.setInt(2, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("진행 중인 보드의 턴 업데이트 실패", e);
        }
    }

    public boolean isNewGame() {
        String query = "SELECT NOT EXISTS (SELECT 1 FROM game WHERE is_end = false)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getBoolean(1);
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("새 게임 확인 실패", e);
        }
    }
}
