package janggi.piece;

import janggi.game.Team;
import janggi.point.Point;
import java.util.List;

public interface Movable {

    boolean isInMovingRange(Point startPoint, Point targetPoint);

    List<Point> findRoute(Point startPoint, Point targetPoint);

    String getName();

    Team getTeam();
}
