package janggi.domain.rule.route;

import janggi.domain.Location;

public enum Direction {

    FRONT(-1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    BACK(1, 0),
    FRONT_LEFT(-1, -1),
    FRONT_RIGHT(-1, 1),
    BACK_LEFT(1, -1),
    BACK_RIGHT(1, 1),
    ;

    private final int rowDiff;
    private final int colDiff;

    Direction(int rowDiff, int colDiff) {
        this.rowDiff = rowDiff;
        this.colDiff = colDiff;
    }

    public Location apply(Location location) {
        return location.add(rowDiff, colDiff);
    }
}
