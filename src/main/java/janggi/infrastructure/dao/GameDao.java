package janggi.infrastructure.dao;

import janggi.infrastructure.dao.dto.GameEntity;
import janggi.infrastructure.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameDao {

    public Long insertGame(Connection connection, String choName, String hanName, String currentTurn) throws SQLException {
        String sql = "INSERT INTO game (cho_player_name, han_player_name, current_turn, is_playing) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, choName);
            preparedStatement.setString(2, hanName);
            preparedStatement.setString(3, currentTurn);
            preparedStatement.setBoolean(4, true);

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
        }
        throw new IllegalStateException("게임 ID가 정상적으로 생성되지 않았습니다.");
    }

    public void updateGame(Connection connection, Long id, String currentTurn, boolean isPlaying) throws SQLException {
        String sql = "UPDATE game SET current_turn = ?, is_playing = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, currentTurn);
            preparedStatement.setBoolean(2, isPlaying);
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();
        }
    }

    public List<GameEntity> findAll() {
        String sql = "SELECT * FROM game ORDER BY id DESC";
        List<GameEntity> games = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                games.add(new GameEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("cho_player_name"),
                        resultSet.getString("han_player_name"),
                        resultSet.getString("current_turn"),
                        resultSet.getBoolean("is_playing")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("게임 목록 조회 중 DB 오류 발생", e);
        }
        return games;
    }

    public Optional<GameEntity> findById(Long id) {
        String sql = "SELECT * FROM game WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new GameEntity(
                            resultSet.getLong("id"),
                            resultSet.getString("cho_player_name"),
                            resultSet.getString("han_player_name"),
                            resultSet.getString("current_turn"),
                            resultSet.getBoolean("is_playing")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("게임 조회 중 DB 오류 발생", e);
        }
        return Optional.empty();
    }
}
