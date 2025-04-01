package janggi.dao;

import janggi.camp.Camp;
import janggi.infra.DatabaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BoardDao {

    public void initializeBoard(Camp firstTurnCamp) {
        String query = "INSERT INTO board (is_end, turn) VALUES (?, ?)";
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
        String query = "SELECT * FROM board WHERE id = ?";
        int boardId = findActiveBoardId();
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, boardId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Camp.from(resultSet.getString("turn"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("현재 턴 조회 실패", e);
        }
        return null;
    }

    public int findActiveBoardId() {
        String query = "SELECT * FROM board WHERE is_end = false";
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

    public void endBoard() {
        String query = "UPDATE board SET is_end = ? WHERE id= ?";
        int boardId = findActiveBoardId();
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, boardId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("게임 종료 처리 실패", e);
        }
    }

    public void updateTurn(Camp camp) {
        String query = "UPDATE board SET turn = ? WHERE id= ?";
        int boardId = findActiveBoardId();
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, camp.getName());
            preparedStatement.setInt(2, boardId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("진행 중인 보드의 턴 업데이트 실패", e);
        }
    }

    public boolean isNewGame() {
        String query = "SELECT EXISTS (SELECT 1 FROM board WHERE is_end = false)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return !resultSet.getBoolean(1);
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("새 게임 확인 실패", e);
        }
    }
}
