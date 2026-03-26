package janggi.domain;

import java.util.ArrayList;
import java.util.List;

public class MovePath {

    private final List<Delta> path;

    public MovePath(List<Delta> path) {
        this.path = path;
    }

    public boolean matches(int dx, int dy) {
        return totalDx() == dx && totalDy() == dy;
    }

    public boolean matchesDirection(int dx, int dy) {
        if (path.size() != 1) {
            return false;
        }
        Delta delta = path.getFirst();
        if (delta.dx() == 0) {
            return dx == 0 && Integer.signum(dy) == Integer.signum(delta.dy()) && dy != 0;
        }
        if (delta.dy() == 0) {
            return dy == 0 && Integer.signum(dx) == Integer.signum(delta.dx()) && dx != 0;
        }
        return Math.abs(dx) == Math.abs(dy)
            && Integer.signum(dx) == Integer.signum(delta.dx())
            && Integer.signum(dy) == Integer.signum(delta.dy());
    }

    private int totalDx() {
        return path.stream()
            .mapToInt(Delta::dx)
            .sum();
    }

    private int totalDy() {
        return path.stream()
            .mapToInt(Delta::dy)
            .sum();
    }

    public List<Position> createRoute(Position start, Position end) {
        List<Position> route = new ArrayList<>();
        Position current = start;
        if (path.size() == 1) {
            return createStraightRoute(end, route, current);
        }
        for (Delta delta : path) {
            current = current.move(delta);
            route.add(current);
        }
        return route;
    }

    public List<Position> intermediatePositions(Position start, Position end) {
        List<Position> route = createRoute(start, end);
        if (route.isEmpty()) {
            return route;
        }
        route.removeLast();
        return route;
    }

    private List<Position> createStraightRoute(Position end, List<Position> route, Position current) {
        Delta delta = path.getFirst();
        while (!current.equals(end)) {
            current = current.move(delta);
            route.add(current);
        }
        return route;
    }
}
