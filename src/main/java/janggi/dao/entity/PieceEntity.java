package janggi.dao.entity;

import janggi.domain.piece.Dynasty;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Point;

public class PieceEntity {

    private Long id;
    private Point point;
    private Dynasty dynasty;
    private PieceType pieceType;
    private Long gameId;

    public PieceEntity(Point point, Dynasty dynasty, PieceType pieceType, Long gameId) {
        this(null, point, dynasty, pieceType, gameId);
    }

    public PieceEntity(Long id, Point point, Dynasty dynasty, PieceType pieceType, Long gameId) {
        this.id = id;
        this.point = point;
        this.dynasty = dynasty;
        this.pieceType = pieceType;
        this.gameId = gameId;
    }

    public Long getId() {
        return id;
    }

    public Point getPoint() {
        return point;
    }

    public Dynasty getDynasty() {
        return dynasty;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Long getGameId() {
        return gameId;
    }
}
