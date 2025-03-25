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

    public boolean canMove(Direction direction) {
        boolean rowValid = Row.isValid(this.rowValue() + direction.getX());
        boolean colValid = Column.isValid(this.columnValue() + direction.getY());

        return rowValid && colValid;
    }

    public Position move(Direction direction) {
        Row row = Row.from(this.rowValue() + direction.getX());
        Column col = Column.from(this.columnValue() + direction.getY());
        return new Position(row, col);
    }

    public boolean isPalacePosition() {
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
