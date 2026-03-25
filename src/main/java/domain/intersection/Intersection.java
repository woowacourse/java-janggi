package domain.intersection;

import domain.piece.NonePiece;
import domain.piece.Piece;
import domain.point.Point;

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

    public Point getPoint() {
        return point;
    }

    public boolean isSamePiece(Intersection intersection) {
        return this.piece.equals(intersection.piece);
    }
}
