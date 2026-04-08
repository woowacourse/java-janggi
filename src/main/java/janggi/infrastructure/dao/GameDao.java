package janggi.infrastructure.dao;

import janggi.infrastructure.dao.dto.GameDto;
import janggi.infrastructure.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class GameDao {

    public Long insertGame(String choName, String hanName, String currentTurn) {
        String sql = "INSERT INTO game (cho_player_name, han_player_name, current_turn, is_playing) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, choName);
            preparedStatement.setString(2, hanName);
            preparedStatement.setString(3, currentTurn);
            preparedStatement.setBoolean(4, true);

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys();) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("게임 정보 저장 중 DB 오류 발생", e);
        }
        throw new IllegalStateException("게임 ID가 정상적으로 생성되지 않았습니다.");
    }

    public Optional<GameDto> findById(Long id) {
        String sql = "SELECT * FROM game WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new GameDto(
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
