package janggi.infra.entity;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;

public class PiecePositionEntity {

    private Long id;
    private Position position;
    private PieceType pieceType;
    private Dynasty dynasty;

    private GameEntity gameEntity;

    public PiecePositionEntity(Position position, PieceType pieceType, Dynasty dynasty, GameEntity gameEntity) {
        this.position = position;
        this.pieceType = pieceType;
        this.dynasty = dynasty;
        this.gameEntity = gameEntity;
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

    public GameEntity gameRoomEntity() {
        return gameEntity;
    }
}
