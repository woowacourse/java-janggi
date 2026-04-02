package janggi.domain.path.generator;

import janggi.domain.board.coordination.Coordination;
import janggi.domain.point.Point;
import janggi.domain.path.Movement;
import java.util.List;

public interface PathStrategy {
    List<Point> calculate(Movement movement, Point from, Coordination coordination);
}
