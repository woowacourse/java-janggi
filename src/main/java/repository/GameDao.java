package repository;

import domain.piece.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class GameDao {

    DBConnection dbConnection;

    public GameDao(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public long save(String turn) {
        String sql = "INSERT INTO game (turn) VALUES (?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, turn);
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) return resultSet.getLong(1);
            }
            throw new SQLException("ID 생성 실패");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTurn(long gameId, String turn) {
        String sql = "UPDATE game SET turn = ? WHERE id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, turn);
            preparedStatement.setLong(2, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("턴 업데이트 중 에러 발생", e);
        }
    }

    public Team findTurn(long gameId) {
        String sql = "SELECT turn FROM game WHERE id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, gameId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Team.valueOf(resultSet.getString("turn"));
                }
            }
            throw new IllegalArgumentException("존재하지 않는 게임 방입니다.");
        } catch (SQLException e) {
            throw new RuntimeException("차례 불러오기 실패", e);
        }
    }

    public Map<Long, String> findAll() {
        String sql = "SELECT id, turn FROM game ORDER BY id DESC";

        java.util.Map<Long, String> games = new java.util.LinkedHashMap<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                games.put(resultSet.getLong("id"), resultSet.getString("turn"));
            }
            return games;
        } catch (SQLException e) {
            throw new RuntimeException("게임 목록 불러오기 실패", e);
        }
    }
}
