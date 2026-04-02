package domain.move.directions;

import domain.intersection.Intersection;
import domain.point.Point;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Directions {

    private final List<Direction> directions;

    public Directions(List<Direction> directions) {
        this.directions = directions;
    }

    public static Directions empty() {
        return new Directions(Collections.emptyList());
    }

    public static Directions cumulative(Vector vector, int maxDistance) {
        List<Direction> cumulativeList = IntStream.rangeClosed(1, maxDistance)
                .mapToObj(i -> new Direction(Collections.nCopies(i, vector)))
                .toList();
        return new Directions(cumulativeList);
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Directions that = (Directions) o;
        return Objects.equals(directions, that.directions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(directions);
    }
}
