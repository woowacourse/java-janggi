package domain;

import java.util.Objects;

public final class Position {
    private final int row;
    private final int column;

    private Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static Position from(int row, int column) {
        return new Position(row, column);
    }

    public Position diff(Position other) {
        return new Position(this.row - other.row, this.column - other.column);
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public int rowDistanceTo(Position other) {
        return other.row - row;
    }

    public int columnDistanceTo(Position other) {
        return other.column - column;
    }

    public boolean isSameColumn(Position other) {
        int rowDistance = rowDistanceTo(other);
        int columnDistance = columnDistanceTo(other);

        if (rowDistance != 0 && columnDistance == 0) {
            return true;
        }

        return false;
    }

    public boolean isSameRow(Position other) {
        int rowDistance = rowDistanceTo(other);
        int columnDistance = columnDistanceTo(other);

        if (rowDistance == 0 && columnDistance != 0) {
            return true;
        }

        return false;
    }

    public boolean isSamePosition(Position other){
        return other.row == row && other.column == column;
    }

    public boolean isPossiblePosition(int maxRow, int minRow, int maxColumn, int minColumn) {
        if (this.row > maxRow || this.row < minRow) {
            return false;
        }
        if (this.column > maxColumn || this.column < minColumn) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
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
