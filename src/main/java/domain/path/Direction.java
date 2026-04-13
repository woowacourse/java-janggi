package domain.path;

import java.util.List;

public enum Direction {
    UP(0, 1),
    DOWN(0, -1),
    RIGHT(1, 0),
    LEFT(-1, 0),
    NORTHEAST(1, 1),
    NORTHWEST(-1, 1),
    SOUTHEAST(1, -1),
    SOUTHWEST(-1, -1);

    private final int deltaX;
    private final int deltaY;

    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public static boolean isLinear(Direction direction){
        List<Direction> linearDirection = List.of(UP, DOWN, RIGHT, LEFT);

        return linearDirection.contains(direction);
    }

    public static Direction decideDirection(int deltaX, int deltaY) {
        if (deltaX == 0 && deltaY == 0) {
            throw new IllegalArgumentException("출발 위치와 도착 위치가 같습니다.");
        }

        if (deltaX == 0 || deltaY == 0) {
            return decideStraightDirection(deltaX, deltaY);
        }

        if (Math.abs(deltaX) == Math.abs(deltaY)) {
            return decideDiagonalDirection(deltaX, deltaY);
        }

        throw new IllegalArgumentException("이동할 수 없는 방향입니다.");
    }

    private static Direction decideStraightDirection(int deltaX, int deltaY) {
        if (deltaX > 0) {
            return RIGHT;
        }

        if (deltaX < 0) {
            return LEFT;
        }

        if (deltaY > 0) {
            return UP;
        }

        return DOWN;
    }

    private static Direction decideDiagonalDirection(int deltaX, int deltaY) {
        if (deltaX > 0) {
            if (deltaY > 0) {
                return NORTHEAST;
            }
            return SOUTHEAST;
        }

        if (deltaY > 0) {
            return NORTHWEST;
        }
        return SOUTHWEST;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }
}
