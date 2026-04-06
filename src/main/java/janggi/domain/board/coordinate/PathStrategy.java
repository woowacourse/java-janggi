package janggi.domain.board.coordinate;

import java.util.List;

import janggi.domain.piece.Pattern;

public interface PathStrategy {

    List<Point> calculate(Pattern pattern, Point from);
}
