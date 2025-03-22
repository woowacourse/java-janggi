package janggi.piece;

import janggi.game.Team;
import janggi.point.Point;
import janggi.point.Route;

public interface Movable {
    String getName();

    Point getPoint();

    Team getTeam();

    Movable updatePoint(Point afterPoint);

    boolean isInMovingRange(Point targetPoint);

    Route findRoute(Point targetPoint);
}
