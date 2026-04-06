package domain.intersection.palace;

import domain.intersection.Intersection;
import domain.move.directions.Directions;
import domain.piece.Piece;
import domain.point.Point;

import static domain.intersection.IntersectionType.NORMAL_INTERSECTION;

public class NormalIntersection extends Intersection {

    public NormalIntersection(Point point, Piece piece) {
        super(NORMAL_INTERSECTION, point, piece);
    }

    public static NormalIntersection empty(Point point) {
        return new NormalIntersection(point, Piece.none());
    }

    @Override
    public boolean isPalace() {
        return false;
    }

    @Override
    public Directions getDiagonalDirections() {
        return Directions.empty();
    }

}
