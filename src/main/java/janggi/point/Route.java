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

    public Crashes findCrashes(Hurdles hurdles) {
        return new Crashes(route.stream()
                .filter(hurdles::containsPoint)
                .toList());
    }

    public boolean isCrashExists(Hurdles hurdles) {
        Crashes crashPoints = findCrashes(hurdles);
        return crashPoints.isPresent();
    }

//    public boolean checkHurdles(Point startPoint, Route route) {
//        List<Point> crashPoints = route.findCrashes(this);
//
//        if (crashPoints.size() == 1
//                && route.findLastPoint().equals(crashPoints.getFirst())
//        ) {
//            Movable crashPiece = findByPoint(crashPoints.getFirst());
//            Movable movingPiece = findByPoint(startPoint);
//            Team crashPieceColor = crashPiece.getTeam();
//            Team movingPieceColor = movingPiece.getTeam();
//
//            return crashPieceColor == movingPieceColor;
//        }
//
//        return !crashPoints.isEmpty();
//    }

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
