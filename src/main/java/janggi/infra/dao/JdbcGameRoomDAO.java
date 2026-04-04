package janggi.infra.dao;

import janggi.infra.entity.GameRoomEntity;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;


public class JdbcGameRoomDAO implements GameRoomDAO {

    private final DataSource dataSource;

    private static final String SAVE_SQL = "INSERT INTO game_room(room_name, last_turn, last_played_at) VALUES(?, ?, ?)";

    public JdbcGameRoomDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Long save(GameRoomEntity gameRoomEntity) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)
        ) {

            pstmt.setString(1, gameRoomEntity.roomName());
            pstmt.setString(2, gameRoomEntity.lastTurn().name());
            pstmt.setTimestamp(3, Timestamp.valueOf(gameRoomEntity.lastPlayedAt()));
            pstmt.executeUpdate();
            return getGeneratedKey(pstmt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static long getGeneratedKey(PreparedStatement pstmt) throws SQLException {
        ResultSet resultSet = pstmt.getGeneratedKeys();
        if(resultSet.next()) {
            return resultSet.getLong(1);
        }
        throw new SQLException("생성된 GameRoom Id를 가져오지 못했습니다.");
    }

    @Override
    public Optional<GameRoomEntity> findById(Long id) {
        return Optional.empty();
    }
}
