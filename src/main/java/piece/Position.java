package piece;

import java.util.Objects;

public class Position {

    private final int row;
    private final int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Position add(Position position) {
        int newRow = this.row + position.row;
        int newColumn = this.column + position.column;

        return new Position(newRow, newColumn);
    }

    public boolean isSameRow(Position position) {
        return this.row == position.row;
    }

    public boolean isSameColumn(Position position) {
        return this.column == position.column;
    }

    private boolean isAbsoluteBigger(Position position) {
        return (this.row + this.column) - (position.row + position.column) > 0;
    }

    public Position getSmallerPosition(Position otherPosition) {
        if (this.isAbsoluteBigger(otherPosition)) {
            return otherPosition;
        }
        return this;
    }

    public Position getBiggerPosition(Position otherPosition) {
        if (this.isAbsoluteBigger(otherPosition)) {
            return this;
        }
        return otherPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}

