package janggi.domain.piece.movepath;

import janggi.domain.piece.Point;
import java.util.List;

public interface MovePath {

    boolean canMove(Point from, Point to);

    List<Point> movePoints(Point from, Point to);
}
