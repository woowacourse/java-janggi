package janggi;

import janggi.point.Point;

public interface Movable {
    Point getPoint();

    TeamColor getColor();

    Movable updatePoint(Point afterPoint);

    boolean isMovable(Point targetPoint);
}
