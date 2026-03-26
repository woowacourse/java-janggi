package janggi.domain.board.coordinate;

import janggi.domain.piece.Direction;
import janggi.domain.piece.Directions;
import java.util.ArrayList;
import java.util.List;

public class FixedPathStrategy implements PathStrategy {
    @Override
    public List<Point> calculate(Directions directions, Point from) {
        List<Point> path = new ArrayList<>();
        for (Direction direction : directions.value()) {
            Point point = from.add(direction.getDx(), direction.getDy());
            path.add(point);
        }
        return path;
    }
}
