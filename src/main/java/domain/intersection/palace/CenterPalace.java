package domain.intersection.palace;

import domain.intersection.Intersection;
import domain.move.directions.Direction;
import domain.move.directions.Directions;
import domain.move.directions.Vector;
import domain.piece.Piece;
import domain.point.Point;

import java.util.List;

import static domain.intersection.IntersectionType.CENTER_PALACE;

public class CenterPalace extends Intersection {

    public CenterPalace(Point point, Piece piece) {
        super(CENTER_PALACE, point, piece);
    }

    public static CenterPalace empty(Point point) {
        return new CenterPalace(point, Piece.none());
    }

    @Override
    public boolean isPalace() {
        return true;
    }

    @Override
    public Directions getDiagonalDirections() {
        return new Directions(List.of(
                new Direction(List.of(Vector.LEFT_UP)),
                new Direction(List.of(Vector.LEFT_DOWN)),
                new Direction(List.of(Vector.RIGHT_UP)),
                new Direction(List.of(Vector.RIGHT_DOWN))
        ));
    }

}
