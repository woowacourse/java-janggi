package domain.intersection.palace;

import domain.intersection.Intersection;
import domain.move.directions.Directions;
import domain.move.directions.Vector;
import domain.piece.Piece;
import domain.point.Point;

public class TopRightPalace extends Intersection {

    public TopRightPalace(Point point, Piece piece) {
        super(point, piece);
    }

    @Override
    public boolean isPalace() {
        return true;
    }

    @Override
    public Directions getDiagonalDirections() {
        if(hasLinearPiece()){
            return Directions.cumulative(Vector.LEFT_DOWN, 2);
        }

        return Directions.cumulative(Vector.LEFT_DOWN, 1);
    }

}
