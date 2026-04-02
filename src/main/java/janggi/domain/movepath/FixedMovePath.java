package janggi.domain.movepath;

import janggi.domain.Delta;
import janggi.domain.Position;
import java.util.ArrayList;
import java.util.List;

public class FixedMovePath implements MovePathStrategy {

    private final List<Delta> path;

    public FixedMovePath(List<Delta> path) {
        this.path = path;
    }

    @Override
    public boolean matches(int dx, int dy) {
        return totalDx() == dx && totalDy() == dy;
    }

    @Override
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

    @Override
    public List<Position> intermediatePositions(Position start, Position end) {
        List<Position> route = createRoute(start, end);
        if (route.isEmpty()) {
            return route;
        }
        route.removeLast();
        return route;
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

    private List<Position> createStraightRoute(Position end, List<Position> route, Position current) {
        Delta delta = path.getFirst();
        while (!current.equals(end)) {
            current = current.move(delta);
            route.add(current);
        }
        return route;
    }
}
