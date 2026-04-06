package janggi.domain.board.coordinate;

import janggi.domain.piece.Pattern;

import java.util.List;

public interface PathStrategy {
    List<Point> calculate(Pattern pattern, Point from);
}
