package janggi.domain.piece.path;

import janggi.domain.board.point.Point;
import janggi.domain.piece.Movement;
import java.util.List;

public interface PathStrategy {
    List<Point> calculate(Movement movement, Point from);
}
