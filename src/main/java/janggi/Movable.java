package janggi;

import janggi.point.Point;
import java.util.List;

public interface Movable {
    Point getPoint();

    TeamColor getColor();

    Movable updatePoint(Point afterPoint);

    boolean isMovable(Point targetPoint);

    List<Point> findRoute(Point targetPoint);
}
