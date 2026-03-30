package domain.intersection;

import domain.intersection.exception.IntersectionException;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.point.Point;

import java.util.Objects;

import static domain.intersection.exception.ErrorMessage.ORIGIN_INTERSECTION_IS_EMPTY;
import static domain.intersection.exception.ErrorMessage.ORIGIN_INTERSECTION_IS_NOT_OPPONENT;

public class Intersection {

    private final Point point;
    private Piece piece;

    public Intersection(Point point, Piece piece) {
        this.point = point;
        this.piece = piece;
    }

    public static Intersection empty(Point point) {
        return new Intersection(point, Piece.none());
    }

    public void move(Intersection to) {
        to.arrive(this);
        leave();
    }

    private void arrive(Intersection from) {
        this.piece = from.piece;
    }

    private void leave() {
        piece = Piece.none();
    }

    public Point getPoint() {
        return point;
    }

    public boolean hasGeneral() {
        return this.piece.isGeneral();
    }

    public boolean isSamePiece(PieceType pieceType) {
        return this.piece.isSamePiece(pieceType);
    }

    public boolean isSameTeam(Team team) {
        return piece.isSameTeam(team);
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

    public boolean isHanIntersection() {
        return piece.isHan();
    }

    public Piece readPiece() {
        return this.piece;
    }

    public void validateMovable(Team currentTeam) {
        validateHasPiece();
        validateSameTeam(currentTeam);
    }

    private void validateSameTeam(Team currentTeam) {
        if (!isSameTeam(currentTeam)) {
            throw new IntersectionException(ORIGIN_INTERSECTION_IS_NOT_OPPONENT);
        }
    }

    private void validateHasPiece() {
        if (!hasPiece()) {
            throw new IntersectionException(ORIGIN_INTERSECTION_IS_EMPTY);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Intersection that = (Intersection) o;
        return Objects.equals(point, that.point) && Objects.equals(piece, that.piece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, piece);
    }

}
