package janggi.domain.piece.path;

import janggi.domain.board.point.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Pattern;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FixedPathStrategy implements PathStrategy {
    @Override
    public List<Point> calculate(Pattern pattern, Point from) {
        List<Point> path = new ArrayList<>();
        Point point = from;
        for (Direction direction : pattern.pattern()) {
            if (!Point.isInRange(point.getX() + direction.getDx(), point.getY() + direction.getDy())) {
                return Collections.emptyList();
            }
            point = point.add(direction.getDx(), direction.getDy());
            path.add(point);
        }
        return path;
    }
}
