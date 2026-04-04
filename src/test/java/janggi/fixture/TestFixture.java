package janggi.fixture;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;
import janggi.infra.entity.GameRoomEntity;
import janggi.infra.entity.PiecePositionEntity;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;

public class TestFixture {

    public static GameRoomEntity createGameRoomEntity(String roomName, Dynasty lastTurn, LocalDateTime lastPlayedAt) {
        return new GameRoomEntity(roomName, lastTurn, lastPlayedAt);
    }

    public static PiecePositionEntity createPiecePositionEntity(
            Position from, PieceType pieceType, Dynasty dynasty, GameRoomEntity gameRoomEntity) {
        return new PiecePositionEntity(from, pieceType, dynasty, gameRoomEntity);
    }

    public static GameRoomEntity saveGameRoomEntity(String roomName, Dynasty lastTurn, LocalDateTime lastPlayedAt, DataSource dataSource) throws SQLException {
        GameRoomEntity gameRoomEntity = createGameRoomEntity(roomName, lastTurn, lastPlayedAt);
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO game_room(room_name, last_turn, last_played_at) VALUES(?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
        ) {
            pstmt.setString(1, gameRoomEntity.roomName());
            pstmt.setString(2, gameRoomEntity.lastTurn().name());
            pstmt.setTimestamp(3, Timestamp.valueOf(gameRoomEntity.lastPlayedAt()));
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long generatedId = rs.getLong(1);
                    return new GameRoomEntity(
                            generatedId,
                            gameRoomEntity.roomName(),
                            gameRoomEntity.lastTurn(),
                            gameRoomEntity.lastPlayedAt()
                    );
                }
            }
        }

        throw new SQLException("생성된 game_room id를 가져오지 못했습니다.");
    }
}
