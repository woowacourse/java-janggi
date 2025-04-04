package domain.direction;

import java.util.Arrays;
import java.util.List;

public enum Direction {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    LEFT_UP(-1, -1),
    RIGHT_UP(-1, 1),
    LEFT_DOWN(1, -1),
    RIGHT_DOWN(1, 1),
    ;

    public final int dr;
    public final int dc;

    Direction(int dr, int dc) {
        this.dr = dr;
        this.dc = dc;
    }

    public boolean isDiagonal() {
        return this == LEFT_DOWN || this == LEFT_UP || this == RIGHT_DOWN || this == RIGHT_UP;
    }

    public static List<Direction> getAll() {
        return Arrays.stream(Direction.values()).toList();
    }
}
