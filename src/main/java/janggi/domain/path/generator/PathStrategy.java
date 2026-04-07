package janggi.domain.path.generator;

import janggi.domain.path.Movement;
import janggi.domain.point.Point;
import java.util.List;
import java.util.function.Predicate;

public interface PathStrategy {
    List<Point> calculate(Movement movement, Point from, Predicate<Point> predicate);
}
