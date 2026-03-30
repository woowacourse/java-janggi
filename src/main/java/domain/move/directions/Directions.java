package domain.move.directions;

import domain.intersection.Intersection;
import domain.point.Point;
import java.util.List;

public class Directions {

    private final List<Direction> directions;

    public Directions(List<Direction> directions) {
        this.directions = directions;
    }

    public List<Point> findPoints(Intersection origin, Intersection destination){
        Point start = origin.getPoint();
        Point end = destination.getPoint();
        return directions.stream()
                .filter(direction -> direction.canReach(start, end))
                .findFirst()
                .map(direction -> direction.getPoints(start))
                .orElseThrow(IllegalArgumentException::new);
    }

}
