package domain.board;

import java.util.ArrayList;
import java.util.List;

public enum Direction {
    UP(0, 1, false),
    DOWN(0, -1, false),
    LEFT(-1, 0, false),
    RIGHT(1, 0, false),
    LEFT_UP(-1, 1, true),
    RIGHT_UP(1, 1, true),
    LEFT_DOWN(-1, -1, true),
    RIGHT_DOWN(1, -1, true),
    ;

    private final int x;
    private final int y;
    private final boolean isDiagonal;

    Direction(int x, int y, boolean isDiagonal) {
        this.x = x;
        this.y = y;
        this.isDiagonal = isDiagonal;
    }

    public static List<Direction> findDirections(int x, int y) {
        List<Direction> directions = new ArrayList<>();
        while (!(x == 0 && y == 0)) {
            Direction direction = findDiagonal(x, y);
            if (x == 0 || y == 0) {
                direction = findStraight(x, y);
            }
            directions.add(direction);
            x += -direction.x;
            y += -direction.y;
        }
        directions.sort((d1, d2) -> Boolean.compare(d1.isDiagonal(), d2.isDiagonal()));
        return directions;
    }

    private static Direction findStraight(int x, int y) {
        if (x == 0 && y < 0) {
            return DOWN;
        }
        if (x == 0 && y > 0) {
            return UP;
        }
        if (x < 0 && y == 0) {
            return LEFT;
        }
        return RIGHT;
    }

    private static Direction findDiagonal(int x, int y) {
        if (x < 0 && y > 0) {
            return LEFT_UP;
        }
        if (x > 0 && y < 0) {
            return RIGHT_DOWN;
        }
        if (x < 0 && y < 0) {
            return LEFT_DOWN;
        }
        return RIGHT_UP;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isDiagonal() {
        return isDiagonal;
    }
}
