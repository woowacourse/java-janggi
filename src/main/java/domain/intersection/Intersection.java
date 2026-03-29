package domain.intersection;

import domain.piece.NonePiece;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.point.Point;
import java.util.Objects;

public class Intersection {

    private final Point point;
    private Piece piece;

    public Intersection(Point point, Piece piece) {
        this.point = point;
        this.piece = piece;
    }

    public static Intersection empty(Point point) {
        return new Intersection(point, new NonePiece());
    }

    public void arrive(Intersection from) {
        this.piece = from.piece;
    }

    public void leave() {
        piece = new NonePiece();
    }

    public Point getPoint() {
        return point;
    }

    public boolean isSamePiece(Intersection intersection) {
        return this.piece.equals(intersection.piece);
    }

    public boolean isSamePiece(PieceType pieceType) {
        return this.piece.isSamePiece(pieceType);
    }

    public boolean isSameTeam(Intersection to) {
        return piece.isSameTeam(to.piece);
    }

    public boolean hasPiece() {
        return piece.hasPiece();
    }

    public boolean isChoIntersection() {
        return piece.isCho();
    }

    public String getChineseCharacter() {
        return piece.getChineseCharacter();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Intersection that = (Intersection) o;
        return Objects.equals(point, that.point) && Objects.equals(piece, that.piece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, piece);
    }

}
