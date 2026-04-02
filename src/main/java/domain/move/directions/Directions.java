package domain.move.directions;

import domain.intersection.Intersection;
import domain.point.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public record Directions(
        List<Direction> directions
) {

    public Directions(List<Direction> directions) {
        this.directions = List.copyOf(directions);
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

    public Directions add(Directions other) {
        List<Direction> combined = new ArrayList<>(this.directions);
        combined.addAll(other.directions);
        return new Directions(combined);
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
