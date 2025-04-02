package board;

import java.util.Objects;
import java.util.Set;

import piece.Direction;

public class Position {

    private static final Set<Position> PALACE_ALL_DIRECTION_POSITIONS = Set.of(
            new Position(1, 4), new Position(1, 6), new Position(2, 5), new Position(3, 4), new Position(3, 6),
            new Position(8, 4), new Position(8, 6), new Position(9, 5), new Position(10, 4), new Position(10, 6)
    );
    private static final Set<Position> PALACE_STRAIGHT_DIRECTION_POSITIONS = Set.of(
            new Position(1, 5), new Position(2, 4), new Position(2, 6), new Position(3, 5),
            new Position(8, 5), new Position(9, 4), new Position(9, 6), new Position(10, 5)
    );

    private static final int MIN_ROW = 1;
    private static final int MIN_COLUMN = 1;
    private static final int MAX_ROW = 10;
    private static final int MAX_COLUMN = 9;

    private final int row;
    private final int column;

    public Position(final int row, final int column) {
        this.row = row;
        this.column = column;
    }

    public boolean isInValidPosition() {
        return (row < MIN_ROW || row > MAX_ROW) ||
               (column < MIN_COLUMN || column > MAX_COLUMN);
    }

    public Position moveByDirection(final Direction direction) {
        return new Position(
                row + direction.getRow(),
                column + direction.getColumn()
        );
    }

    public boolean isPalacePosition() {
        return PALACE_ALL_DIRECTION_POSITIONS.contains(this) || PALACE_STRAIGHT_DIRECTION_POSITIONS.contains(this);
    }

    public boolean hasDiagonalDirectionInPosition() {
        return PALACE_ALL_DIRECTION_POSITIONS.contains(this);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position position)) {
            return false;
        }
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "Position{" +
               "row=" + row +
               ", column=" + column +
               '}';
    }

}
