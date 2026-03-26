package domain.board;

import domain.intersection.Intersection;
import domain.point.Point;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class JanggiBoard {

    private static final int MAX_ROW = 10;
    private static final int MAX_FILE = 9;

    private final Map<Point, Intersection> intersections;

    public JanggiBoard(IntersectionGenerator intersectionGenerator) {
        this.intersections = fillEmptyIntersections();
        for (Intersection intersection : intersectionGenerator.makeIntersection()) {
            intersections.put(intersection.getPoint(), intersection);
        }
    }

    private static Map<Point, Intersection> fillEmptyIntersections() {
        return getAllPoints()
                .collect(Collectors.toMap(point -> point, Intersection::empty));
    }

    private static Stream<Point> getAllPoints() {
        return range(MAX_ROW).boxed()
                .flatMap(row -> range(MAX_FILE).mapToObj(f -> new Point(row, f)));
    }

    private static IntStream range(int maxRange) {
        return IntStream.range(0, maxRange);
    }

    public Intersection findIntersection(Point point) {
        return intersections.get(point);
    }

}
