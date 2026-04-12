package domain.intersection;

import domain.intersection.exception.IntersectionException;
import domain.move.directions.Directions;
import domain.piece.Piece;
import domain.piece.PieceScore;
import domain.piece.Team;
import domain.point.Point;

import java.util.Objects;

import static domain.intersection.exception.IntersectionError.ORIGIN_INTERSECTION_IS_EMPTY;
import static domain.intersection.exception.IntersectionError.ORIGIN_INTERSECTION_IS_NOT_OPPONENT;

public abstract class Intersection {

    private final IntersectionType intersectionType;
    private final Point point;
    private Piece piece;

    protected Intersection(IntersectionType intersectionType, Point point, Piece piece) {
        this.intersectionType = intersectionType;
        this.point = point;
        this.piece = piece;
    }

    public void move(Intersection destination) {
        destination.arrive(this);
        leave();
    }

    public abstract boolean isPalace();

    public abstract Directions getDiagonalDirections();

    private void arrive(Intersection origin) {
        this.piece = origin.piece;
    }

    private void leave() {
        piece = Piece.none();
    }

    public void validateMovable(Team currentTeam) {
        validateHasPiece();
        validateSameTeam(currentTeam);
    }

    private void validateSameTeam(Team currentTeam) {
        if (!isSameTeam(currentTeam)) {
            throw new IntersectionException(ORIGIN_INTERSECTION_IS_NOT_OPPONENT.getMessage());
        }
    }

    private void validateHasPiece() {
        if (!hasPiece()) {
            throw new IntersectionException(ORIGIN_INTERSECTION_IS_EMPTY.getMessage());
        }
    }

    public boolean hasGeneral() {
        return this.piece.isGeneral();
    }

    public boolean hasCannon() {
        return this.piece.isCannon();
    }

    public boolean hasPiece() {
        return piece.hasPiece();
    }

    public boolean isSameTeam(Team team) {
        return piece.isSameTeam(team);
    }

    public boolean isSameTeam(Intersection destination) {
        return piece.isSameTeam(destination.piece);
    }

    public int getScore() {
        return PieceScore.getScore(piece);
    }

    public Team getTeam() {
        return piece.team();
    }

    public IntersectionType getType() {
        return this.intersectionType;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public Point getPoint() {
        return point;
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
