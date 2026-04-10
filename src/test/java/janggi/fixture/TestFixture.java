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

    public static GameEntity createGameRoomEntity(Long id, RoomName roomName, Dynasty lastTurn, LocalDateTime lastPlayedAt) {
        return new GameEntity(id, roomName, lastTurn, lastPlayedAt);
    }


    public static PiecePositionEntity createPiecePositionEntity(
            Position from, PieceType pieceType, Dynasty dynasty, Long gameId) {
        return new PiecePositionEntity(from, pieceType, dynasty, gameId);
    }

    public static GameEntity saveGameRoomEntity(RoomName roomName, Dynasty lastTurn, LocalDateTime lastPlayedAt, DataSource dataSource) throws SQLException {
        GameEntity gameEntity = createGameRoomEntity(roomName, lastTurn, lastPlayedAt);
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO game(room_name, current_turn, last_played_at) VALUES(?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
        ) {
            pstmt.setString(1, gameEntity.roomName().roomName());
            pstmt.setString(2, gameEntity.currentTurn().name());
            pstmt.setTimestamp(3, Timestamp.valueOf(gameEntity.lastPlayedAt()));
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long generatedId = rs.getLong(1);
                    gameEntity.bindId(generatedId);
                    return gameEntity;
                }
            }
        }

        throw new SQLException("생성된 game_room id를 가져오지 못했습니다.");
    }

    public static PiecePositionEntity savePiecePositionEntity(
            Position from, PieceType pieceType, Dynasty dynasty, Long gameId, DataSource dataSource) throws SQLException {
        PiecePositionEntity piecePositionEntity = createPiecePositionEntity(from, pieceType, dynasty, gameId);
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO piece_position(game_id, piece_row, piece_column, piece_type, dynasty) VALUES(?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
        ) {
            pstmt.setLong(1, piecePositionEntity.gameId());
            pstmt.setInt(2, piecePositionEntity.position().row().row());
            pstmt.setInt(3, piecePositionEntity.position().column().column());
            pstmt.setString(4, piecePositionEntity.pieceType().name());
            pstmt.setString(5, piecePositionEntity.dynasty().name());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long generatedId = rs.getLong(1);
                    piecePositionEntity.bindId(generatedId);
                    return piecePositionEntity;
                }
            }
        }

        throw new SQLException("생성된 game_room id를 가져오지 못했습니다.");
    }
}
