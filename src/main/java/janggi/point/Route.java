package janggi.point;

import janggi.game.Board;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Route {
    private final List<Point> route;

    public Route(List<Point> route) {
        this.route = route;
    }

    public static Route repeat(Direction direction, Point startPoint, Point targetPoint) {
        List<Point> route = new ArrayList<>();
        Point pointer = startPoint;
        while (!pointer.equals(targetPoint)) {
            try {
                pointer = pointer.move(direction.getRowOffset(), direction.getColumnOffset());
                route.add(pointer);
            } catch (IllegalArgumentException ignore) {
            }
        }
        return new Route(route);
    }

    public static Route follow(List<Direction> directions, Point startPoint) {
        List<Point> route = new ArrayList<>();
        Point pointer = startPoint;
        for (Direction direction : directions) {
            try {
                pointer = pointer.move(direction.getRowOffset(), direction.getColumnOffset());
                route.add(pointer);
            } catch (IllegalArgumentException ignore) {
            }
        }
        return new Route(route);
    }

    public Point findLastPoint() {
        return route.getLast();
    }

    public boolean isCrashExists(Hurdles hurdles) {
        return route.stream()
                .anyMatch(point -> hurdles.containsPoint(point));
    }

    public List<Point> findCrashes(Hurdles hurdles) {
        return route.stream()
                .filter(hurdles::containsPoint)
                .toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Route target = (Route) o;
        return target.route.containsAll(this.route)
                && this.route.containsAll(target.route);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(route);
    }
}
