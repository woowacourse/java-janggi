package piece;

import java.util.Arrays;
import java.util.List;

public enum Direction {

    LEFT(0, -1),
    LEFT_TOP(-1, -1),
    LEFT_BOTTOM(1, -1),
    RIGHT(0, 1),
    RIGHT_TOP(-1, 1),
    RIGHT_BOTTOM(1, 1),
    TOP(-1, 0),
    BOTTOM(1, 0),
    ;

    private final int row;
    private final int column;

    Direction(final int row, final int column) {
        this.row = row;
        this.column = column;
    }

    public static List<Direction> getStraightDirection() {
        return Arrays.stream(values())
                .filter(direction -> direction.row == 0 || direction.column == 0)
                .toList();
    }

    public static List<Direction> getDiagonalDirection() {
        return Arrays.stream(values())
                .filter(Direction::isDiagonal)
                .toList();
    }

    public boolean isDiagonal() {
        return this.row != 0 && this.column != 0;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

}
