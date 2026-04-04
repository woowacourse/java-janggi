package janggi.fixture;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.RoomName;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;
import janggi.infra.entity.GameEntity;
import janggi.infra.entity.PiecePositionEntity;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;

public class TestFixture {

    public static GameEntity createGameRoomEntity(RoomName roomName, Dynasty lastTurn, LocalDateTime lastPlayedAt) {
        return new GameEntity(roomName, lastTurn, lastPlayedAt);
    }

    public static PiecePositionEntity createPiecePositionEntity(
            Position from, PieceType pieceType, Dynasty dynasty, GameEntity gameEntity) {
        return new PiecePositionEntity(from, pieceType, dynasty, gameEntity);
    }

    public static GameEntity saveGameRoomEntity(RoomName roomName, Dynasty lastTurn, LocalDateTime lastPlayedAt, DataSource dataSource) throws SQLException {
        GameEntity gameEntity = createGameRoomEntity(roomName, lastTurn, lastPlayedAt);
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO game(room_name, last_turn, last_played_at) VALUES(?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
        ) {
            pstmt.setString(1, gameEntity.roomName().name());
            pstmt.setString(2, gameEntity.lastTurn().name());
            pstmt.setTimestamp(3, Timestamp.valueOf(gameEntity.lastPlayedAt()));
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long generatedId = rs.getLong(1);
                    return new GameEntity(
                            generatedId,
                            gameEntity.roomName(),
                            gameEntity.lastTurn(),
                            gameEntity.lastPlayedAt()
                    );
                }
            }
        }

        throw new SQLException("생성된 game_room id를 가져오지 못했습니다.");
    }
}
