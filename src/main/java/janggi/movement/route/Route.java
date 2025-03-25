package janggi.movement.route;

import janggi.piece.Movable;
import janggi.point.Point;
import janggi.movement.crash.Crashes;
import janggi.movement.direction.Direction;
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
            pointer = addPointInRangeToRoute(direction, pointer, route);
        }
        return new Route(route);
    }

    public static Route follow(List<Direction> directions, Point startPoint) {
        List<Point> route = new ArrayList<>();
        Point pointer = startPoint;
        for (Direction direction : directions) {
            pointer = addPointInRangeToRoute(direction, pointer, route);
        }
        return new Route(route);
    }

    private static Point addPointInRangeToRoute(Direction direction, Point pointer, List<Point> route) {
        try {
            pointer = pointer.move(direction.getRowOffset(), direction.getColumnOffset());
            route.add(pointer);
        } catch (IllegalArgumentException ignore) {
        }
        return pointer;
    }

    public boolean hasNoCrash(Hurdles hurdles) {
        return route.stream().anyMatch(hurdles::containsPoint);
    }

    public boolean hasOnlyPassables(Movable movingPiece, Point targetPoint, Hurdles hurdles) {
        // TODO route 중 중간경로와, 최종 목적지에 대한 고려사항이 다름
        Crashes crashes = findCrashes(hurdles, movingPiece);
        return crashes.hasOnlyPassables(movingPiece.getTeam(), targetPoint, hurdles);
    }

    private Crashes findCrashes(Hurdles hurdles, Movable movingPiece) {
        List<Point> crashPoints = route.stream()
                .filter(hurdles::containsPoint)
                .toList();
        return Crashes.fromPieceType(movingPiece, crashPoints);
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
