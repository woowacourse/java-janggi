package domain.piece.move;

import domain.point.Point;
import java.util.ArrayList;
import java.util.List;

public class Direction {

    private final List<Vector> vectors;

    public Direction(List<Vector> vectors) {
        this.vectors = vectors;
    }

    public static Direction straight(Vector vector, int distance) {
        final List<Vector> vectors = new ArrayList<>();
        for (int i = 0; i < distance; i++) {
            vectors.add(vector);
        }
        return new Direction(List.copyOf(vectors));
    }

    public boolean canReach(Point start, Point target) {
        int dy = vectors.stream().mapToInt(Vector::dy).sum();
        int dx = vectors.stream().mapToInt(Vector::dx).sum();

        if (start.canMake(dy, dx)) {
            Point destination = start.movePoint(dy, dx);
            return destination.equals(target);
        }

        return false;
    }

    public List<Point> getPoints(Point current) {
        List<Point> points = new ArrayList<>();
        for (Vector vector : this.vectors) {
            current = current.next(vector);
            points.add(current);
        }
        return points;
    }

}
