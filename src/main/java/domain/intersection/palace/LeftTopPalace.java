package domain.intersection.palace;

import domain.intersection.Intersection;
import domain.move.directions.Directions;
import domain.move.directions.Vector;
import domain.piece.Piece;
import domain.point.Point;

public class LeftTopPalace extends Intersection {

    public LeftTopPalace(Point point, Piece piece) {
        super(point, piece);
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
        if (hasLinearPiece()) {
            return Directions.cumulative(Vector.RIGHT_DOWN, 2);
        }

        return Directions.cumulative(Vector.RIGHT_DOWN, 1);
    }

}
