package janggi.domain.path.generator;

import janggi.domain.path.Direction;
import janggi.domain.path.Movement;
import janggi.domain.point.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SinglePathStrategy implements PathStrategy {
    private static final int SINGLE_SIZE = 1;

    @Override
    public List<Point> calculate(Movement movement, Point from, Predicate<Point> predicate) {
        if (movement.getDirections().size() != SINGLE_SIZE) {
            throw new IllegalStateException("SinglePath는 Movement의 Direction이 한개여야 합니다.");
        }
        Direction dir = movement.getDirections().getFirst();

        List<Point> points = new ArrayList<>();
        Point nextPoint = from.add(dir.getDx(), dir.getDy());
        if (predicate.test(nextPoint)) {
            points.add(nextPoint);
        }

        return points;
    }
}
