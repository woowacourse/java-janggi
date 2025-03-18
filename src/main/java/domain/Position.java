package domain;

import java.util.Objects;

public class Position {
    private final Row row;
    private final Column column;

    private Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public static Position of(int row, int column) {
        return new Position(new Row(row), new Column(column));
    }


    public boolean checkPositionAfterDeltaMove(int deltaRow, int deltaColumn, Position expectedPosition) {
        if (row.canMoveRow(deltaRow) && column.canMoveColumn(deltaColumn)) {
            return expectedPosition.equals(new Position(row.moveRow(deltaRow), column.moveColumn(deltaColumn)));
        }
        return false;
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
        return Objects.equals(row, position.row) && Objects.equals(column, position.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
