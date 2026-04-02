package domain.intersection.palace;

import domain.intersection.Intersection;
import domain.move.directions.Directions;
import domain.piece.Piece;
import domain.point.Point;

public class NormalPalace extends Intersection {

    public NormalPalace(Point point, Piece piece) {
        super(point, piece);
    }

    public static NormalPalace empty(Point point) {
        return new NormalPalace(point, Piece.none());
    }

    @Override
    public boolean isPalace() {
        return true;
    }

    @Override
    public Directions getDiagonalDirections() {
        return Directions.empty();
    }

}
