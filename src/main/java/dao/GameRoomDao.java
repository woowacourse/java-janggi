package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameRoomDao {

    public long save(Connection connection, GameRoomRawData room) {
        String sql = "INSERT INTO game_room (name, current_turn, status, consecutive_pass_count) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, room.name());
            statement.setString(2, room.currentTurn());
            statement.setString(3, room.status());
            statement.setInt(4, room.consecutivePassCount());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
                throw new IllegalStateException("게임방 ID 생성 실패");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("게임방 저장 실패", e);
        }
    }

    public List<GameRoomRawData> findAll(Connection connection) {
        String sql = "SELECT id, name, current_turn, status, consecutive_pass_count FROM game_room ORDER BY id";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<GameRoomRawData> rooms = new ArrayList<>();
            while (resultSet.next()) {
                rooms.add(mapRow(resultSet));
            }
            return rooms;
        } catch (SQLException e) {
            throw new IllegalStateException("게임방 목록 조회 실패", e);
        }
    }

    public Optional<GameRoomRawData> findById(Connection connection, long id) {
        String sql = "SELECT id, name, current_turn, status, consecutive_pass_count FROM game_room WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("게임방 조회 실패", e);
        }
    }

    public void update(Connection connection, GameRoomRawData room) {
        String sql = "UPDATE game_room SET current_turn = ?, status = ?, consecutive_pass_count = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, room.currentTurn());
            statement.setString(2, room.status());
            statement.setInt(3, room.consecutivePassCount());
            statement.setLong(4, room.id());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("게임방 갱신 실패", e);
        }
    }

    private GameRoomRawData mapRow(ResultSet resultSet) throws SQLException {
        return new GameRoomRawData(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("current_turn"),
                resultSet.getString("status"),
                resultSet.getInt("consecutive_pass_count")
        );
    }
}
