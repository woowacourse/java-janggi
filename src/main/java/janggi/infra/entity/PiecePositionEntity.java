package janggi.infra.entity;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;

public class PiecePositionEntity {

    private Long id;
    private Position position;
    private PieceType pieceType;
    private Dynasty dynasty;

    private Long gameId;

    public PiecePositionEntity(Position position, PieceType pieceType, Dynasty dynasty, Long gameId) {
        this.position = position;
        this.pieceType = pieceType;
        this.dynasty = dynasty;
        this.gameId = gameId;
    }

    public PiecePositionEntity(Long id, Position position, PieceType pieceType, Dynasty dynasty, Long gameId) {
        this.id = id;
        this.position = position;
        this.pieceType = pieceType;
        this.dynasty = dynasty;
        this.gameId = gameId;
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

    public Long gameId() {
        return gameId;
    }

    public void bindId(long id) {
        if(this.id == null) {
            this.id = id;
        }
    }
}
