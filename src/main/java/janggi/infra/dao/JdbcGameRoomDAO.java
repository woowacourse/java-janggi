package janggi.infra.dao;

import janggi.infra.entity.GameRoomEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;


public class JdbcGameRoomDAO implements GameRoomDAO {

    private final DataSource dataSource;

    private static final String SAVE_SQL = "INSERT INTO game_room(room_name, last_turn, last_played_at) VALUES(?, ?, ?)";

    public JdbcGameRoomDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(GameRoomEntity gameRoomEntity) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(SAVE_SQL)
        ) {

            pstmt.setString(1, gameRoomEntity.roomName());
            pstmt.setString(2, gameRoomEntity.lastTurn().name());
            pstmt.setTimestamp(3, Timestamp.valueOf(gameRoomEntity.lastPlayedAt()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<GameRoomEntity> findById(Long id) {
        return Optional.empty();
    }
}
