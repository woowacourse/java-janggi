package janggi.domain.piece.path.generator;

import janggi.domain.piece.path.Direction;
import janggi.domain.piece.path.Movement;
import janggi.domain.point.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class FixedPathStrategy implements PathStrategy {
    @Override
    public List<Point> calculate(Movement movement, Point from, Predicate<Point> predicate) {
        List<Point> points = new ArrayList<>();
        Point nextPoint = from;
        for (Direction direction : movement.getDirections()) {
            nextPoint = nextPoint.add(direction.getDx(), direction.getDy());
            if (!predicate.test(nextPoint)) {
                return Collections.emptyList();
            }
            points.add(nextPoint);
        }
        return points;
    }
}
