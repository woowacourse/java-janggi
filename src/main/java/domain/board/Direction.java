package domain.board;

import java.util.List;

public record Direction(int column, int row) {

    public static final Direction UP = new Direction(-1, 0);
    public static final Direction RIGHT_UP = new Direction(-1, 1);
    public static final Direction RIGHT = new Direction(0, 1);
    public static final Direction RIGHT_DOWN = new Direction(1, 1);
    public static final Direction DOWN = new Direction(1, 0);
    public static final Direction LEFT_DOWN = new Direction(1, -1);
    public static final Direction LEFT = new Direction(0, -1);
    public static final Direction LEFT_UP = new Direction(-1, -1);

    public static final List<Direction> ORTHOGONAL_DIRECTIONS = List.of(
            Direction.UP,
            Direction.RIGHT,
            Direction.DOWN,
            Direction.LEFT
    );

    public static final List<Direction> DIAGONAL_DIRECTIONS = List.of(
            Direction.RIGHT_UP,
            Direction.RIGHT_DOWN,
            Direction.LEFT_DOWN,
            Direction.LEFT_UP
    );

    public static Direction of(final int column, final int row) {
        return new Direction(column, row);
    }
}
