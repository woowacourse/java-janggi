package domain.intersection.palace;

import domain.intersection.Intersection;
import domain.move.directions.Directions;
import domain.move.directions.Vector;
import domain.piece.Piece;
import domain.point.Point;

import static common.constant.JanggiConstant.CORNER_PALACE_MAX_DISTANCE;
import static domain.intersection.IntersectionType.RIGHT_TOP_PALACE;

public class RightTopPalace extends Intersection {

    public RightTopPalace(Point point, Piece piece) {
        super(RIGHT_TOP_PALACE, point, piece);
    }

    public static RightTopPalace empty(Point point) {
        return new RightTopPalace(point, Piece.none());
    }

    @Override
    public boolean isPalace() {
        return true;
    }

    @Override
    public Directions getDiagonalDirections() {
        return Directions.cumulative(Vector.LEFT_DOWN, CORNER_PALACE_MAX_DISTANCE);
    }

}
