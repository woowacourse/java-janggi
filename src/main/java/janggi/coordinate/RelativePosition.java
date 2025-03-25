package janggi.coordinate;

import janggi.coordinate.Position;

public enum RelativePosition {
    TOP(-1, 0),
    BOTTOM(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    TOP_LEFT_DIAGONAL(-1, -1),
    TOP_RIGHT_DIAGONAL(-1, 1),
    BOTTOM_LEFT_DIAGONAL(1, -1),
    BOTTOM_RIGHT_DIAGONAL(1, 1);

    private final int x;
    private final int y;

    RelativePosition(final int x, final int y) {
        this.x = x;
        this.y = y;
    }


    public Position calculateNextPosition(final Position now) {
        return now.plusPosition(x, y);
    }
}
