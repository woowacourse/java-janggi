package domain.piece.move;

import domain.point.Point;
import java.util.List;

public class Directions {

    private final List<Direction> directions;

    public Directions(List<Direction> directions) {
        this.directions = directions;
    }

    public List<Point> findPoints(Point from, Point to) {
        for (Direction direction : directions) {
            if (direction.canReach(from, to)) {
                return direction.getPoints(from);
            }
        }
        throw new IllegalArgumentException();
    }

}
