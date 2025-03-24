package janggi.domain.piece.movement;

import janggi.domain.board.Point;
import java.util.List;

public interface MovePath {

    boolean canMove(Point from, Point to);

    List<Point> movePoints(Point from, Point to);
}
