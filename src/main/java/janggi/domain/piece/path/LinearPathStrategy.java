package janggi.domain.piece.path;

import janggi.domain.board.Dimension;
import janggi.domain.board.point.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Movement;
import java.util.ArrayList;
import java.util.List;

public class LinearPathStrategy implements PathStrategy {
    private static final int LINEAR_SIZE = 1;

    @Override
    public List<Point> calculate(Movement movement, Point from, Dimension dimension) {
        if (movement.pattern().size() != LINEAR_SIZE) {
            throw new IllegalArgumentException("방향은 %d개여야 합니다.".formatted(LINEAR_SIZE));
        }

        return createLinearPoints(from, movement.pattern().getFirst(), dimension);
    }

    private List<Point> createLinearPoints(Point from, Direction direction, Dimension dimension) {
        List<Point> path = new ArrayList<>();
        int dx = direction.getDx();
        int dy = direction.getDy();

        while (dimension.isInRange(from.x() + dx, from.y() + dy)) {
            Point point = from.add(dx, dy);
            path.add(point);
            dx += direction.getDx();
            dy += direction.getDy();
        }
        return path;
    }
}
