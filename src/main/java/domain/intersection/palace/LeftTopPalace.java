package domain.intersection.palace;

import domain.intersection.Intersection;
import domain.move.directions.Directions;
import domain.move.directions.Vector;
import domain.piece.Piece;
import domain.point.Point;

import static common.constant.JanggiConstant.CORNER_PALACE_MAX_DISTANCE;
import static domain.intersection.IntersectionType.LEFT_TOP_PALACE;

public class LeftTopPalace extends Intersection {

    public LeftTopPalace(Point point, Piece piece) {
        super(LEFT_TOP_PALACE, point, piece);
    }

    public static LeftTopPalace empty(Point point) {
        return new LeftTopPalace(point, Piece.none());
    }

    @Override
    public boolean isPalace() {
        return true;
    }

    @Override
    public Directions getDiagonalDirections() {
        return Directions.cumulative(Vector.RIGHT_DOWN, CORNER_PALACE_MAX_DISTANCE);
    }

}
