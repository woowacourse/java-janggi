package repository.impl;

import config.H2ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import entity.GameRoomEntity;
import repository.GameRoomRepository;

public class GameRoomRepositoryImpl implements GameRoomRepository {

    private static final String INSERT_SQL =
            "INSERT INTO game_room (name) VALUES (?)";

    private static final String SELECT_BY_ID_SQL =
            "SELECT id, name, created_at FROM game_room WHERE id = ?";

    private static final String SELECT_ALL =
            "SELECT id, name, created_at FROM game_room";

    @Override
    public long save(String name, Connection conn) {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.executeUpdate();

            return getGeneratedId(stmt);

        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 룸 저장 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public List<GameRoomEntity> findAll() {
        try (Connection conn = H2ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL)) {

            List<GameRoomEntity> result;
            try (ResultSet rs = stmt.executeQuery()) {
                result = extractList(rs);
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 룸 조회 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public boolean existsById(long id) {
        try (Connection conn = H2ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            List<GameRoomEntity> result;
            try (ResultSet rs = stmt.executeQuery()) {
                result = extractList(rs);
            }
            return !result.isEmpty();

        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 룸 조회 중 오류가 발생했습니다.", e);
        }
    }

    private List<GameRoomEntity> extractList(ResultSet rs) throws SQLException {
        List<GameRoomEntity> result = new ArrayList<>();
        while (rs.next()) {
            result.add(toGameRoom(rs));
        }
        return result;
    }

    private long getGeneratedId(PreparedStatement stmt) throws SQLException {
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        }
        throw new RuntimeException("[ERROR] ID 생성에 실패했습니다.");
    }

    private GameRoomEntity toGameRoom(ResultSet rs) throws SQLException {
        return new GameRoomEntity(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}