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
        int nextRow = this.getRow();
        int nextColumn = this.getColumn();
        return (
                nextRow < MIN_ROW || nextRow > MAX_ROW ||
                        nextColumn < MIN_COLUMN || nextColumn > MAX_COLUMN
        );
    }

    public Position nextPosition(final Direction direction) {
        return new Position(
                row + direction.getRow(),
                column + direction.getColumn()
        );
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
        return getRow() == position.getRow() && getColumn() == position.getColumn();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getColumn());
    }

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }

}
