package janggi.piece;

import janggi.game.Team;
import janggi.point.Point;
import java.util.List;

public interface Movable {
    String getName();

    Point getPoint();

    Team getTeam();

    Movable updatePoint(Point afterPoint);

    boolean isInMovingRange(Point targetPoint);

    List<Point> findRoute(Point targetPoint);
}
