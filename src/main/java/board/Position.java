package board;

import java.awt.Point;
import java.util.Objects;

import piece.Direction;

public class Position {

    private final Point point;
    public static final int MIN_ROW = 1;
    public static final int MIN_COLUMN = 1;
    public static final int MAX_ROW = 10;
    public static final int MAX_COLUMN = 9;

    public Position(final int column, final int row) {
        this.point = new Point(column, row);
    }

    public int getColumn() {
        return point.x;
    }

    public int getRow() {
        return point.y;
    }

    public boolean isInValidPosition() {
        int nextColumn = this.getColumn();
        int nextRow = this.getRow();
        return (
                nextColumn < MIN_COLUMN ||
                nextColumn > MAX_COLUMN ||
                nextRow < MIN_ROW ||
                nextRow > MAX_ROW
        );
    }

    public Position nextPosition(final Direction direction) {
        return new Position(
                getColumn() + direction.getDeltaColumn(),
                getRow() + direction.getDeltaRow()
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
        return Objects.equals(point, position.point);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(point);
    }

    @Override
    public String toString() {
        return "\nPosition{" +
                "point=" + point +
                '}';
    }
}
