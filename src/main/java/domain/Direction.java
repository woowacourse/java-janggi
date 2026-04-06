package domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public enum Direction implements Comparator<Direction> {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    LEFT_UP(-1, 1),
    RIGHT_UP(1, 1),
    LEFT_DOWN(-1, -1),
    RIGHT_DOWN(1, -1),
    ;

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static List<Direction> findDirections(int x, int y) {
        List<Direction> directions = new ArrayList<>();
        while (!(x == 0 && y == 0)) {
            Direction direction = findDirection(x, y);
            directions.add(direction);
            x += -direction.x;
            y += -direction.y;
        }
        directions.sort((d1, d2) -> Boolean.compare(d1.isDiagonal(), d2.isDiagonal()));
        return directions;
    }

    private static Direction findDirection(int x, int y) {
        if (x == 0 || y == 0) {
            return findStraight(x, y);
        }
        return findDiagonal(x, y);
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

    @Override
    public int compare(Direction direction1, Direction direction2) {
        if (!direction1.isDiagonal() && direction2.isDiagonal()) {
            return -1;
        }
        if (direction1.isDiagonal() && !direction2.isDiagonal()) {
            return 1;
        }
        return 0;
    }

    public boolean isDiagonal() {
        return x != 0 && y != 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
