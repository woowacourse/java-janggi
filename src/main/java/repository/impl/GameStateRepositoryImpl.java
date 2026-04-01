package repository.impl;

import java.sql.Connection;
import domain.place.piece.Side;
import entity.GameStateEntity;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import repository.GameStateRepository;

public class GameStateRepositoryImpl implements GameStateRepository {

    private static final String INSERT_SQL =
            "INSERT INTO game_state (game_room_id, current_turn) VALUES (?,?)";

    private static final String SELECT_BY_ID_SQL =
            "SELECT game_room_id, current_turn FROM game_state WHERE game_room_id = ?";

    @Override
    public void save(long roomId, Side turn, Connection conn) {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {

            stmt.setLong(1, roomId);
            stmt.setString(2, turn.getName());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 룸 저장 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public GameStateEntity findByRoomId(long roomId, Connection conn) {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setLong(1, roomId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return toGameState(rs);
                }
            }

            throw new IllegalArgumentException("[ERROR] 해당 게임 스테이터스는 존재하지 않습니다.");

        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 스테이터스 조회 중 오류가 발생했습니다.", e);
        }
    }

    private GameStateEntity toGameState(ResultSet rs) throws SQLException {
        String currentTurn = rs.getString("current_turn");
        return GameStateEntity.of(
                rs.getLong("game_room_id"),
                Side.from(currentTurn));
    }
}
