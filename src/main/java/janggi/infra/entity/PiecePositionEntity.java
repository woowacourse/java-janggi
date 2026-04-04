package janggi.infra.entity;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;

public class PiecePositionEntity {

    private Long id;
    private Position position;
    private PieceType pieceType;
    private Dynasty dynasty;

    private GameRoomEntity gameRoomEntity;

    public PiecePositionEntity(Position position, PieceType pieceType, Dynasty dynasty, GameRoomEntity gameRoomEntity) {
        this.position = position;
        this.pieceType = pieceType;
        this.dynasty = dynasty;
        this.gameRoomEntity = gameRoomEntity;
    }

    public Long id() {
        return id;
    }

    public Position position() {
        return position;
    }

    public PieceType pieceType() {
        return pieceType;
    }

    public Dynasty dynasty() {
        return dynasty;
    }

    public GameRoomEntity gameRoomEntity() {
        return gameRoomEntity;
    }
}
