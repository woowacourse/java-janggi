package janggi.domain;

import java.util.ArrayList;
import java.util.List;

public class MovePath {

    private final List<Delta> path;

    public MovePath(List<Delta> path) {
        this.path = path;
    }

    public boolean matches(int dx, int dy) {
        return totalDelta().equals(Delta.of(dx, dy));
    }

    public boolean matchesDirection(int dx, int dy) {
        if (path.size() != 1) {
            return false;
        }
        Delta inputDelta = Delta.scaleDown(dx, dy);
        Delta delta = path.getFirst();

        return inputDelta.equals(delta);
    }

    public List<Position> intermediatePositions(Position start, Position end) {
        List<Position> route = createRoute(start, end);
        if (route.isEmpty()) {
            return route;
        }
        route.removeLast();
        return route;
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

    private Delta totalDelta() {
        return path.stream()
                .reduce(Delta.zero(), Delta::add);
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
