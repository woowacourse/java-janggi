package janggi.domain.board.path;

import janggi.domain.board.point.Point;
import janggi.domain.piece.Pattern;
import java.util.List;

public interface PathStrategy {
    List<Point> calculate(Pattern pattern, Point from);
}
