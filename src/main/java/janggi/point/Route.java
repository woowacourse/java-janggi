package janggi.point;

import janggi.piece.Movable;
import janggi.piece.Po;
import janggi.point.crash.CrashOrBridgeAndPrey;
import janggi.point.crash.CrashOrPrey;
import janggi.point.crash.Crashes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Route {
    private final List<Point> route;

    public Route(List<Point> route) {
        this.route = route;
    }

    //TODO 이동가능한 루트가 아예 없다면? (가능한가?)
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

    public Crashes findCrashes(Hurdles hurdles, Movable movable) {
        List<Point> crashPoints = route.stream()
                .filter(hurdles::containsPoint)
                .toList();
        if (movable instanceof Po) {
            return new CrashOrBridgeAndPrey(crashPoints);
        }
        return new CrashOrPrey(crashPoints);
    }

    public boolean isCrashExists(Hurdles hurdles) {
        return route.stream().anyMatch(hurdles::containsPoint);
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
