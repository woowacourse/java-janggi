package janggi.fixture;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;
import janggi.infra.entity.GameRoomEntity;
import janggi.infra.entity.PiecePositionEntity;

import java.time.LocalDateTime;

public class TestFixture {

    public static GameRoomEntity createGameRoomEntity() {
        return new GameRoomEntity("room1", Dynasty.HAN,
                LocalDateTime.of(2026, 4, 3, 15, 30));
    }

    public static PiecePositionEntity createPiecePositionEntity(
            Position from, PieceType pieceType, Dynasty dynasty, GameRoomEntity gameRoomEntity) {
        return new PiecePositionEntity(from, pieceType, dynasty, gameRoomEntity);
    }
}
