package janggi.domain.piece.path.generator;

import janggi.domain.piece.path.Movement;
import janggi.domain.point.Point;
import java.util.List;
import java.util.function.Predicate;

public interface PathStrategy {
    List<Point> calculate(Movement movement, Point from, Predicate<Point> predicate);
}
