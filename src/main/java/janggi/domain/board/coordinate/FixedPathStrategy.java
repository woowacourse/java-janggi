package janggi.domain.board.coordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import janggi.domain.board.Board;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Pattern;

public class FixedPathStrategy implements PathStrategy {

    @Override
    public List<Point> calculate(Pattern pattern, Point from) {
        List<Point> path = new ArrayList<>();
        Point point = from;
        for (Direction direction : pattern.directions()) {
            if (!Board.isInBoard(point.x() + direction.getDx(), point.y() + direction.getDy())) {
                return Collections.emptyList();
            }
            point = point.add(direction.getDx(), direction.getDy());
            path.add(point);
        }
        return path;
    }
}
