package repository.impl;

import domain.place.piece.Side;
import java.sql.Connection;
import dto.GameRoomDto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import repository.GameRoomRepository;

public class GameRoomRepositoryImpl implements GameRoomRepository {

    private static final String INSERT_SQL =
            "INSERT INTO game_room (name, current_turn) VALUES (?,?)";

    private static final String SELECT_BY_ID_SQL =
            "SELECT id, name, current_turn, created_at FROM game_room WHERE id = ?";

    private static final String SELECT_ALL =
            "SELECT id, name, current_turn, created_at FROM game_room";

    @Override
    public long save(String name, String side,Connection conn) {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.setString(2, side);
            stmt.executeUpdate();

            return getGeneratedId(stmt);

        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 룸 저장 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public List<GameRoomDto> findAll(Connection conn) {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_ALL)) {

            List<GameRoomDto> result;
            try (ResultSet rs = stmt.executeQuery()) {
                result = extractList(rs);
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 룸 조회 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public Optional<GameRoomDto> findById(long id, Connection conn) {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if(rs.next())
                    return Optional.of(toGameRoom(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 룸 조회 중 오류가 발생했습니다.", e);
        }
    }

    private List<GameRoomDto> extractList(ResultSet rs) throws SQLException {
        List<GameRoomDto> result = new ArrayList<>();
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

    private GameRoomDto toGameRoom(ResultSet rs) throws SQLException {
        return GameRoomDto.of(
                rs.getLong("id"),
                rs.getString("name"),
                Side.from(rs.getString("current_turn")),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
