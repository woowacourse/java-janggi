package janggi.point;

import janggi.piece.Movable;
import janggi.point.crash.Crashes;
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

    public boolean hasNoHurdle(Movable movingPiece, Point targetPoint, Hurdles hurdles) {
        if (isCrashExists(hurdles)) {
            Crashes crashes = findCrashes(hurdles, movingPiece);
            return crashes.hasNoCrashes(movingPiece.getTeam(), targetPoint, hurdles);
        }
        return true;
    }

    private Crashes findCrashes(Hurdles hurdles, Movable movable) {
        List<Point> crashPoints = route.stream()
                .filter(hurdles::containsPoint)
                .toList();
        return Crashes.fromPieceType(movable, crashPoints);
    }

    private boolean isCrashExists(Hurdles hurdles) {
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
        return 0;
    }
}
