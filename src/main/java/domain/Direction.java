package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum Direction implements Comparator<Direction> {
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
    private final boolean isDialog;

    Direction(int x, int y, boolean isDialog) {
        this.x = x;
        this.y = y;
        this.isDialog = isDialog;
    }

    public static List<Direction> findDirections(int x, int y) {
        List<Direction> directions = new ArrayList<>();
        while (!(x == 0 && y == 0)) {
            Direction direction = null;
            if (x == 0 || y == 0) {
                direction = findStraight(x, y);
            }
            if (x != 0 && y != 0) {
                direction = findDialog(x, y);
            }
            directions.add(direction);
            x += -direction.x;
            y += -direction.y;
        }
        Collections.sort(directions);
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

    private static Direction findDialog(int x, int y) {
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

    public boolean isDialog() {
        return isDialog;
    }

    @Override
    public int compare(Direction direction1, Direction direction2) {
        if (!direction1.isDialog && direction2.isDialog) {
            return -1;
        }
        if (direction1.isDialog && !direction2.isDialog) {
            return 1;
        }
        return 0;
    }
}
