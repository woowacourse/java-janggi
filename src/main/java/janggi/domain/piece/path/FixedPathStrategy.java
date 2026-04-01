package janggi.domain.piece.path;

import janggi.domain.board.Dimension;
import janggi.domain.board.point.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Movement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FixedPathStrategy implements PathStrategy {
    @Override
    public List<Point> calculate(Movement movement, Point from, Dimension dimension) {
        List<Point> points = new ArrayList<>();
        Point point = from;
        for (Direction direction : movement.pattern()) {
            if (!dimension.isInRange(point.x() + direction.getDx(), point.y() + direction.getDy())) {
                return Collections.emptyList();
            }
            point = point.add(direction.getDx(), direction.getDy());
            points.add(point);
        }
        return points;
    }
}
