package janggi.infrastructure.dao;

import janggi.infrastructure.dao.dto.GameEntity;
import janggi.infrastructure.db.ConnectionContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameDao {

    private Connection getConnection() {
        return ConnectionContext.get();
    }

    public Long insertGame(String choName, String hanName, String choFormation, String hanFormation, String currentTurn) throws SQLException {
        String sql = "INSERT INTO game (cho_player_name, han_player_name, cho_formation, han_formation, current_turn, is_playing) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, choName);
            preparedStatement.setString(2, hanName);
            preparedStatement.setString(3, choFormation);
            preparedStatement.setString(4, hanFormation);
            preparedStatement.setString(5, currentTurn);
            preparedStatement.setBoolean(6, true);

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
        }
        throw new IllegalStateException("게임 ID가 정상적으로 생성되지 않았습니다.");
    }

    public void updateGame(Long id, String currentTurn, boolean isPlaying) throws SQLException {
        String sql = "UPDATE game SET current_turn = ?, is_playing = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, currentTurn);
            preparedStatement.setBoolean(2, isPlaying);
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();
        }
    }

    public List<GameEntity> findAll() {
        String sql = "SELECT * FROM game ORDER BY id DESC";
        List<GameEntity> games = new ArrayList<>();

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                games.add(mapToEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("게임 목록 조회 중 DB 오류 발생", e);
        }
        return games;
    }

    public Optional<GameEntity> findById(Long id) {
        String sql = "SELECT * FROM game WHERE id = ?";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapToEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("게임 조회 중 DB 오류 발생", e);
        }
        return Optional.empty();
    }

    private GameEntity mapToEntity(ResultSet resultSet) throws SQLException {
        return new GameEntity(
                resultSet.getLong("id"),
                resultSet.getString("cho_player_name"),
                resultSet.getString("han_player_name"),
                resultSet.getString("cho_formation"),
                resultSet.getString("han_formation"),
                resultSet.getString("current_turn"),
                resultSet.getBoolean("is_playing")
        );
    }
}
