package janggi.domain.rule.route;

import janggi.domain.Location;

public enum Direction {

    FRONT(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    BACK(0, -1),
    FRONT_LEFT(-1, 1),
    FRONT_RIGHT(1, 1),
    BACK_LEFT(-1, -1),
    BACK_RIGHT(1, -1);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Location apply(Location location) {
        return location.add(dy, dx);
    }
}
