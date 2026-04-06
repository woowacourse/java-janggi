package janggi.domain.board.coordinate;

import java.util.ArrayList;
import java.util.List;

import janggi.domain.board.Board;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Pattern;

public class LinearPathStrategy implements PathStrategy {

    private static final int LINEAR_SIZE = 1;

    @Override
    public List<Point> calculate(Pattern pattern, Point from) {
        if (pattern.directions().size() != LINEAR_SIZE) {
            throw new IllegalArgumentException("방향은 %d개여야 합니다.".formatted(LINEAR_SIZE));
        }

        return createLinearPoints(from, pattern.directions().getFirst());
    }

    private List<Point> createLinearPoints(Point from, Direction direction) {
        List<Point> path = new ArrayList<>();
        int dx = direction.getDx();
        int dy = direction.getDy();

        while (Board.isInBoard(from.x() + dx, from.y() + dy)) {
            Point point = from.add(dx, dy);
            path.add(point);
            dx += direction.getDx();
            dy += direction.getDy();
        }
        return path;
    }
}
