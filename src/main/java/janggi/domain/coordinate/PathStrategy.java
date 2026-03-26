package janggi.domain.coordinate;

import java.util.List;

public interface PathStrategy {
    List<Point> calculate(List<Direction> directions, Point from);
}
