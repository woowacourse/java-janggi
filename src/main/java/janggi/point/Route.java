package janggi.point;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private final List<Point> route;

    public Route(List<Point> route) {
        this.route = route;
    }

    public static Route repeat(Direction direction, Point startPoint, Point targetPoint) {
        List<Point> route = new ArrayList<>();
        Point pointer = startPoint;
        while (pointer != targetPoint) {
            route.add(pointer);
            pointer = startPoint.move(direction.getRowOffset(), direction.getColumnOffset());
        }
        return new Route(route);
    }
}
