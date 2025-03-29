package janggi.domain.piece.move;

import janggi.domain.board.Point;

@FunctionalInterface
public interface PathCalculator {
    Path calculate(Point start, Point end);
}