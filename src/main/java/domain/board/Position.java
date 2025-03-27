package domain.board;

import java.util.Objects;
import java.util.Set;

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
        return (Set.of(1, 3, 8, 10).contains(rowValue()) && Set.of(4, 6).contains(columnValue()))
                || (Set.of(2, 9)).contains(rowValue()) && Set.of(5).contains(columnValue());
    }

    public boolean isInPalace() {
        return (Set.of(1, 2, 3, 8, 9, 10)).contains(rowValue()) && (Set.of(4, 5, 6)).contains(columnValue());
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
