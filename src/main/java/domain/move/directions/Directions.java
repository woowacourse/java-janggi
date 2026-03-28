package domain.move.directions;

import domain.intersection.Intersection;
import domain.point.Point;
import java.util.List;

public class Directions {

    private final List<Direction> directions;

    public Directions(List<Direction> directions) {
        this.directions = directions;
    }

    public List<Point> findPoints(Intersection from, Intersection to){
        Point start = from.getPoint();
        Point end = to.getPoint();
        return directions.stream()
                .filter(direction -> direction.canReach(start, end))
                .findFirst()
                .map(direction -> direction.getPoints(start))
                .orElseThrow(IllegalArgumentException::new);
    }

}
