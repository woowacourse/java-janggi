package janggi.dao;

import janggi.domain.camp.Camp;
import janggi.infra.DatabaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDao {

    private final DatabaseConnector DBConnector;

    public GameDao(DatabaseConnector DBConnector) {
        this.DBConnector = DBConnector;
    }

    public void initializeGame(Camp firstTurnCamp) {
        String query = "INSERT INTO game (is_end, turn) VALUES (?, ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBoolean(1, false);
            preparedStatement.setString(2, firstTurnCamp.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("게임 초기화 중 오류가 발생했습니다.", e);
        }
    }

    public Camp findLatestTurn() {
        String query = "SELECT * FROM game WHERE id = ?";
        int gameId = findActiveGameId();
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Camp.from(resultSet.getString("turn"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("현재 게임의 턴 조회 중 오류가 발생했습니다.", e);
        }
        throw new IllegalStateException("현재 게임의 턴을 찾을 수 없습니다.");
    }

    public int findActiveGameId() {
        String query = "SELECT * FROM game WHERE is_end = false";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("진행 중인 게임 ID 조회 중 오류가 발생했습니다.", e);
        }
        throw new IllegalStateException("진행 중인 게임을 찾을 수 없습니다.");
    }

    public void endGame() {
        String query = "UPDATE game SET is_end = ? WHERE id= ?";
        int gameId = findActiveGameId();
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("게임 종료 처리 중 오류가 발생했습니다.", e);
        }
    }

    public void updateTurn(Camp camp) {
        String query = "UPDATE game SET turn = ? WHERE id= ?";
        int gameId = findActiveGameId();
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, camp.getName());
            preparedStatement.setInt(2, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("턴 정보 업데이트 중 오류가 발생했습니다.", e);
        }
    }

    public boolean isNewGame() {
        String query = "SELECT NOT EXISTS (SELECT 1 FROM game WHERE is_end = false)";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getBoolean(1);
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("새 게임 여부 확인 중 오류가 발생했습니다.", e);
        }
    }
}
