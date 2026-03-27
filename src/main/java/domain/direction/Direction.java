package domain.direction;

import domain.position.Position;

public enum Direction {

    NORTH(0, 1),
    SOUTH(0, -1),
    EAST(1, 0),
    WEST(-1, 0),
    NORTH_EAST(1, 1),
    NORTH_WEST(-1, 1),
    SOUTH_EAST(1, -1),
    SOUTH_WEST(-1, -1);

    private final int offsetX;
    private final int offsetY;

    Direction(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public Position move(Position src) {
        return new Position(src.x() + this.offsetX, src.y() + this.offsetY);
    }
}
