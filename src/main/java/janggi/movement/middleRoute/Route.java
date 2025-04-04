package janggi.movement.middleRoute;

import janggi.piece.Piece;
import janggi.point.Point;
import janggi.movement.direction.Direction;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Route {
    private final List<Point> route;

    public Route(List<Point> route) {
        this.route = route;
    }

    public static Route repeat(Direction direction, Point startPoint, Point targetPoint) {
        List<Point> route = new ArrayList<>();
        Point pointer = startPoint.move(direction.getRowOffset(), direction.getColumnOffset());
        while (!pointer.equals(targetPoint)) {
            try {
                route.add(pointer);
                pointer = pointer.move(direction.getRowOffset(), direction.getColumnOffset());
            } catch (IllegalArgumentException ignore) {
                throw new IllegalStateException("시스템에 문제가 발생했습니다.");
            }
        }
        return new Route(route);
    }

    public static Route follow(List<Direction> directions, Point startPoint) {
        List<Point> route = new ArrayList<>();
        Point pointer = startPoint;
        for (int i = 0; i < directions.size() - 1; i++) {
            try {
                Direction direction = directions.get(i);
                pointer = pointer.move(direction.getRowOffset(), direction.getColumnOffset());
                route.add(pointer);
            } catch (IllegalArgumentException ignore) {
                throw new IllegalStateException("시스템에 문제가 발생했습니다.");
            }
        }
        return new Route(route);
    }

    public boolean hasCrash(Hurdles hurdles) {
        return route.stream().anyMatch(hurdles::containsPoint);
    }

    public Piece findFirstCrash(Hurdles hurdles) {
        return route.stream()
                .filter(hurdles::containsPoint)
                .map(hurdles::findByPoint)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    public int countCrashes(Hurdles hurdles) {
        return (int) route.stream().
                filter(hurdles::containsPoint)
                .count();
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
        return new HashSet<>(target.route).containsAll(this.route)
                && new HashSet<>(this.route).containsAll(target.route);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
