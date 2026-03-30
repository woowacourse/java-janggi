package janggi.domain;

import janggi.domain.position.Position;

import java.util.List;

public class Path {
    private final List<Position> route;
    private final Position destination;

    public Path(List<Position> route, Position destination) {
        this.route = route;
        this.destination = destination;
    }

    public Position destination() {
        return destination;
    }

    public boolean hasDestination(Position position) {
        return destination == position;
    }

    public boolean hasRoute(Position currentPosition) {
        return route.stream()
                .anyMatch(position -> position == currentPosition);
    }
}
