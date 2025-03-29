package janggi.domain.direction;

import janggi.domain.position.Position;

public enum Direction {

    NONE(0, 0),
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP_LEFT(-1, 1),
    UP_RIGHT(1, 1),
    DOWN_LEFT(-1, -1),
    DOWN_RIGHT(1, -1);

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Direction get(Position origin, Position destination) {
        int xOffset = Integer.compare(destination.getX(), origin.getX());
        int yOffset = Integer.compare(destination.getY(), origin.getY());

        for (Direction direction : values()) {
            if (direction.x == xOffset && direction.y == yOffset) {
                return direction;
            }
        }
        throw new IllegalArgumentException("잘못된 방향입니다.");
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
