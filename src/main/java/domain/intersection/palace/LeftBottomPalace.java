package domain.intersection.palace;

import domain.intersection.Intersection;
import domain.move.directions.Directions;
import domain.move.directions.Vector;
import domain.piece.Piece;
import domain.point.Point;

import static common.constant.JanggiConstant.CORNER_PALACE_MAX_DISTANCE;
import static domain.intersection.IntersectionType.LEFT_BOTTOM_PALACE;

public class LeftBottomPalace extends Intersection {

    public LeftBottomPalace(Point point, Piece piece) {
        super(LEFT_BOTTOM_PALACE, point, piece);
    }

    public static LeftBottomPalace empty(Point point) {
        return new LeftBottomPalace(point, Piece.none());
    }

    @Override
    public boolean isPalace() {
        return true;
    }

    @Override
    public Directions getDiagonalDirections() {
        return Directions.cumulative(Vector.RIGHT_UP, CORNER_PALACE_MAX_DISTANCE);
    }

}
