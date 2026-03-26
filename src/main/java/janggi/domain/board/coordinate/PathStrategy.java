package janggi.domain.board.coordinate;

import janggi.domain.piece.Directions;
import java.util.List;

public interface PathStrategy {
    List<Point> calculate(Directions directions, Point from);
}
