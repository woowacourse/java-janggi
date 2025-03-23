package janggi.piece;

import janggi.game.Team;
import janggi.point.Hurdles;
import janggi.point.Point;

public interface Movable {
    String getName();

    Point getPoint();

    Team getTeam();

    Movable updatePoint(Point afterPoint);

    boolean canMove(Point targetPoint, Hurdles hurdles);
}
