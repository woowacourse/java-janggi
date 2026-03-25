package domain.board;

import domain.intersection.Intersection;
import domain.piece.NonePiece;
import domain.point.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiBoard {
    private final Map<Point, Intersection> intersections;

    public JanggiBoard(IntersectionGenerator intersectionGenerator) {
        this.intersections = fillEmptyIntersections();
        setInitialIntersections(this.intersections, intersectionGenerator.makeIntersection());
    }

    private static Map<Point, Intersection> fillEmptyIntersections() {
        Map<Point, Intersection> intersections = new HashMap<>();
        NonePiece nonePiece = new NonePiece();
        for (int row = 0; row < 10; row++) {
            for (int file = 0; file < 9; file++) {
                Point point = new Point(row, file);
                intersections.put(point, new Intersection(point, nonePiece));
            }
        }
        return intersections;
    }

    private static void setInitialIntersections(Map<Point, Intersection> intersections,
                                                List<Intersection> initialIntersections) {
        for (Intersection intersection : initialIntersections) {
            intersections.put(intersection.getPoint(), intersection);
        }
    }

    public Intersection getIntersection(Point point) {
        return intersections.get(point);
    }
}
