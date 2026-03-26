package janggi.domain.board.coordinate;

import janggi.domain.piece.Direction;
import janggi.domain.piece.Directions;
import java.util.ArrayList;
import java.util.List;

public class LinearPathStrategy implements PathStrategy {
    private static final int LINEAR_SIZE = 1;

    @Override
    public List<Point> calculate(Directions directions, Point from) {
        if (directions.value().size() != LINEAR_SIZE) {
            throw new IllegalArgumentException("방향은 %d개여야 합니다.".formatted(LINEAR_SIZE));
        }

        return createLinearPoints(from, directions.value().getFirst());
    }

    private List<Point> createLinearPoints(Point from, Direction direction) {
        List<Point> path = new ArrayList<>();
        int dx = direction.getDx();
        int dy = direction.getDy();

        while (Point.isInRange(from.x() + dx, from.y() + dy)) {
            Point point = from.add(dx, dy);
            path.add(point);
            dx += direction.getDx();
            dy += direction.getDy();
        }
        return path;
    }
}
