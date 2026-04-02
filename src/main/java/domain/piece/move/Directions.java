package domain.piece.move;

import domain.point.Point;
import java.util.List;

public class Directions {

    private final List<Direction> directions;

    public Directions(List<Direction> directions) {
        this.directions = directions;
    }

    public List<Point> findPoints(Point from, Point to) {
        return directions.stream()
                .filter(direction -> direction.canReach(from, to))
                .findFirst()
                .map(direction -> direction.getPoints(from))
                .orElseThrow(IllegalArgumentException::new);
    }

}
