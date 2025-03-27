package janggi.domain.board;

import java.util.Objects;

public class Position {
    private final Row row;
    private final Column column;

    public Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public static Position of(int row, int col) {
        return new Position(Row.from(row), Column.from(col));
    }

    public boolean canMove(Movement movement) {
        boolean rowValid = Row.isValid(this.rowValue() + movement.movedX());
        boolean colValid = Column.isValid(this.columnValue() + movement.movedY());

        return rowValid && colValid;
    }

    public Position move(Movement movement) {
        Row row = Row.from(this.rowValue() + movement.movedX());
        Column col = Column.from(this.columnValue() + movement.movedY());
        return new Position(row, col);
    }

    public boolean inPalace() {
        return Palace.isInPalace(this);
    }

    public int rowValue() {
        return row.intValue();
    }

    public int columnValue() {
        return column.intValue();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position position)) return false;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
