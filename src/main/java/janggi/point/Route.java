package janggi.point;

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
            pointer = pointer.move(direction.getRowOffset(), direction.getColumnOffset());
            route.add(pointer);
        }
        return new Route(route);
    }

    public static Route follow(List<Direction> directions, Point startPoint) {
        List<Point> route = new ArrayList<>();
        Point pointer = startPoint;
        for (Direction direction : directions) {
            pointer = pointer.move(direction.getRowOffset(), direction.getColumnOffset());
            route.add(pointer);
        }
        return new Route(route);
    }

    public Point findLastPoint() {
        return route.getLast();
    }

    public List<Point> findCrashes(List<Point> piecePoints) {
        return route.stream()
                .filter(piecePoints::contains)
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
