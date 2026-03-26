package janggi.domain.board.coordinate;

import janggi.domain.piece.Direction;
import java.util.List;

public interface PathStrategy {
    List<Point> calculate(List<Direction> directions, Point from);
}
