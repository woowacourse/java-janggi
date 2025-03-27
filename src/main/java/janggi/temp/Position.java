package janggi.temp;

import java.util.List;

public record Position(Column column, Row row) {

    private static final List<Position> palacePositions = List.of(
            new Position(Column.THREE, Row.ZERO),
            new Position(Column.FOUR, Row.ZERO),
            new Position(Column.FIVE, Row.ZERO),
            new Position(Column.THREE, Row.ONE),
            new Position(Column.FOUR, Row.ONE),
            new Position(Column.FIVE, Row.ONE),
            new Position(Column.THREE, Row.TWO),
            new Position(Column.FOUR, Row.TWO),
            new Position(Column.FIVE, Row.TWO),
            new Position(Column.THREE, Row.SEVEN),
            new Position(Column.FOUR, Row.SEVEN),
            new Position(Column.FIVE, Row.SEVEN),
            new Position(Column.THREE, Row.EIGHT),
            new Position(Column.FOUR, Row.EIGHT),
            new Position(Column.FIVE, Row.EIGHT),
            new Position(Column.THREE, Row.NINE),
            new Position(Column.FOUR, Row.NINE),
            new Position(Column.FIVE, Row.NINE)
    );

    public boolean isPalace() {
        return palacePositions.contains(this);
    }

    public Position move(final Movement movement) {
        final Column targetColumn = column.move(movement.getColumnValue());
        final Row targetRow = row.move(movement.getRowValue());
        return new Position(targetColumn, targetRow);
    }

    public boolean canMove(final Movement movement) {
        final boolean canMoveColumn = column.canMove(movement.getColumnValue());
        final boolean canMoveRow = row.canMove(movement.getRowValue());
        return canMoveColumn && canMoveRow;
    }
}
