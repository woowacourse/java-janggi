package domain.intersection;

import domain.board.IntersectionState;
import domain.piece.NonePiece;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.point.Point;
import domain.team.Team;

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

    public Team getTeam() {
        return piece.getTeam();
    }

    public boolean isSamePiece(Intersection intersection) {
        return this.piece.isSamePiece(intersection.piece);
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

    public IntersectionState toIntersectionState() {
        return new IntersectionState(point, piece.pieceType(), piece.getTeam());
    }

}
