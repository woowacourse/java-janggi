package janggi.domain.piece.move;

import janggi.domain.board.Point;
import janggi.domain.piece.Path;

@FunctionalInterface
public interface PathCalculator {
    Path calculate(Point start, Point end);
}