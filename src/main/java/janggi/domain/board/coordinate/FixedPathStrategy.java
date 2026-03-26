package janggi.domain.board.coordinate;

import janggi.domain.piece.Direction;
import java.util.ArrayList;
import java.util.List;

public class FixedPathStrategy implements PathStrategy {
    @Override
    public List<Point> calculate(List<Direction> directions, Point from) {
        List<Point> path = new ArrayList<>();
        for (Direction direction : directions) {
            Point point = from.add(direction.getDx(), direction.getDy());
            path.add(point);
        }
        return path;
    }
}
