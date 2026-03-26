package janggi.domain;

import java.util.List;

public class Path {
    private final List<Position> route;
    private final Position destination;

    public Path(List<Position> route, Position destination) {
        this.route = route;
        this.destination = destination;
    }
}
