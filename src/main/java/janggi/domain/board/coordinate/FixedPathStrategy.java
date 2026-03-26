package janggi.domain.board.coordinate;

import janggi.domain.piece.Direction;
import janggi.domain.piece.Pattern;
import java.util.ArrayList;
import java.util.List;

public class FixedPathStrategy implements PathStrategy {
    @Override
    public List<Point> calculate(Pattern pattern, Point from) {
        List<Point> path = new ArrayList<>();
        for (Direction direction : pattern.pattern()) {
            Point point = from.add(direction.getDx(), direction.getDy());
            path.add(point);
        }
        return path;
    }
}
