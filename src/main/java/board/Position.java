package board;

import java.util.Objects;

import piece.Direction;

public class Position {

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
