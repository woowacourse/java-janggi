package domain.intersection.palace;

import domain.intersection.Intersection;
import domain.move.directions.Directions;
import domain.move.directions.Vector;
import domain.piece.Piece;
import domain.point.Point;

import static common.constant.JanggiConstant.CORNER_PALACE_MAX_DISTANCE;
import static domain.intersection.IntersectionType.RIGHT_BOTTOM_PALACE;

public class RightBottomPalace extends Intersection {

    public RightBottomPalace(Point point, Piece piece) {
        super(RIGHT_BOTTOM_PALACE, point, piece);
    }

    public static RightBottomPalace empty(Point point) {
        return new RightBottomPalace(point, Piece.none());
    }

    @Override
    public boolean isPalace() {
        return true;
    }

    @Override
    public Directions getDiagonalDirections() {
        return Directions.cumulative(Vector.LEFT_UP, CORNER_PALACE_MAX_DISTANCE);
    }

}
