package domain.intersection;

import domain.board.IntersectionState;
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
        return new Intersection(point, new Piece(PieceType.NONE));
    }

    public void arrive(Intersection from) {
        this.piece = from.piece;
    }

    public void leave() {
        piece = new Piece(PieceType.NONE);
    }

    public Point getPoint() {
        return point;
    }

    public boolean isPalace() {
        return point.isPalace();
    }

    public boolean isPalaceDiagonal() {
        return point.isPalaceDiagonal();
    }

    public Team getTeam() {
        return piece.getTeam();
    }

    public boolean isSamePiece(Intersection intersection) {
        PieceType otherPieceType = intersection.piece.pieceType();
        return this.piece.isSamePiece(otherPieceType);
    }

    public boolean isSamePiece(PieceType pieceType) {
        return this.piece.isSamePiece(pieceType);
    }

    public boolean isSameTeam(Team team) {
        return piece.isSameTeam(team);
    }

    public boolean hasPiece() {
        return piece.hasPiece();
    }

    public IntersectionState toIntersectionState() {
        return new IntersectionState(point, piece.pieceType(), piece.getTeam());
    }
}
