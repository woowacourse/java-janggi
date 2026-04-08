package infrastructure.repository;

import domain.room.GameRoom;
import infrastructure.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class H2GameRoomRepository implements GameRoomRepository {

    private final DatabaseManager databaseManager;

    public H2GameRoomRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public List<GameRoom> findAllPlaying() {
        String sql = "SELECT id, name FROM game_rooms WHERE status = 'PLAYING' ORDER BY id";
        List<GameRoom> rooms = new ArrayList<>();
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                rooms.add(new GameRoom(resultSet.getLong("id"), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 게임방 목록 조회 실패: " + e.getMessage(), e);
        }
        return rooms;
    }

    @Override
    public GameRoom save(String name) {
        String sql = "INSERT INTO game_rooms (name) VALUES (?)";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                return new GameRoom(keys.getLong(1), name);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 게임방 생성 실패: " + e.getMessage(), e);
        }
    }

    @Override
    public void finish(long roomId) {
        String sql = "UPDATE game_rooms SET status = 'FINISHED' WHERE id = ?";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, roomId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 게임방 종료 처리 실패: " + e.getMessage(), e);
        }
    }
}
