package domain.board;

import java.util.Objects;

public class Position {
    private final Row row;
    private final Column column;

    public Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public int rowDifference(Position other) {
        return other.rowValue() - row.getValue();
    }

    public int columnDifference(Position other) {
        return other.columnValue() - column.getValue();
    }

    public boolean canGoDiagonal() {
        if ((row.getValue() == 1 || row.getValue() == 3 || row.getValue() == 8 || row.getValue() == 10) && (
                column.getValue() == 4 || column.getValue() == 6)) {
            return true;
        }
        if ((row.getValue() == 2 || row.getValue() == 9) && column.getValue() == 5) {
            return true;
        }
        return false;
    }

    public boolean isInPalace() {
        return row.isPalaceExist() && column.isPalaceExist();
    }

    public int rowValue() {
        return row.getValue();
    }

    public int columnValue() {
        return column.getValue();
    }

    @Override
    public String toString() {
        return row.getValue() + ", " + column.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position position)) {
            return false;
        }
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
