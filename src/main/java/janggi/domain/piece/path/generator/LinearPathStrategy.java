package janggi.domain.piece.path.generator;

import janggi.domain.piece.path.Direction;
import janggi.domain.piece.path.Movement;
import janggi.domain.point.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LinearPathStrategy implements PathStrategy {
    private static final int LINEAR_SIZE = 1;

    @Override
    public List<Point> calculate(Movement movement, Point from, Predicate<Point> predicate) {
        if (movement.getDirections().size() != LINEAR_SIZE) {
            throw new IllegalArgumentException("방향은 %d개여야 합니다.".formatted(LINEAR_SIZE));
        }

        return createLinearPoints(from, movement.getDirections().getFirst(), predicate);
    }

    private List<Point> createLinearPoints(Point from, Direction direction, Predicate<Point> predicate) {
        List<Point> path = new ArrayList<>();
        Point nextPoint = from.add(direction.getDx(), direction.getDy());

        while (predicate.test(nextPoint)) {
            path.add(nextPoint);
            nextPoint = nextPoint.add(direction.getDx(), direction.getDy());
        }
        return path;
    }
}
