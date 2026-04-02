package domain.intersection.palace;

import domain.intersection.Intersection;
import domain.move.directions.Directions;
import domain.piece.Piece;
import domain.point.Point;

public class NormalIntersection extends Intersection {

    public NormalIntersection(Point point, Piece piece) {
        super(point, piece);
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
