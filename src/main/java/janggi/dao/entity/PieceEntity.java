package janggi.dao.entity;

import janggi.domain.piece.Dynasty;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Point;
import java.util.Objects;

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

    public Piece createPiece() {
        return getPieceType().createPiece(dynasty);
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

    public void setId(long id) {
        this.id = id;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PieceEntity that = (PieceEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(point, that.point)
                && dynasty == that.dynasty && pieceType == that.pieceType && Objects.equals(gameId, that.gameId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(point);
        result = 31 * result + Objects.hashCode(dynasty);
        result = 31 * result + Objects.hashCode(pieceType);
        result = 31 * result + Objects.hashCode(gameId);
        return result;
    }
}
