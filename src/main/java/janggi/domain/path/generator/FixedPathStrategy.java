package janggi.domain.path.generator;

import janggi.domain.board.coordination.Coordination;
import janggi.domain.point.Point;
import janggi.domain.path.Direction;
import janggi.domain.path.Movement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FixedPathStrategy implements PathStrategy {
    @Override
    public List<Point> calculate(Movement movement, Point from, Coordination coordination) {
        List<Point> points = new ArrayList<>();
        Point point = from;
        for (Direction direction : movement.getDirections()) {
            if (!coordination.isInRange(point.x() + direction.getDx(), point.y() + direction.getDy())) {
                return Collections.emptyList();
            }
            point = point.add(direction.getDx(), direction.getDy());
            points.add(point);
        }
        return points;
    }
}
