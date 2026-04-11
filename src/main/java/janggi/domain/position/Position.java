package janggi.domain.position;

import java.util.Objects;

public class Position {

    private static final int LENGTH_OF_POSITION_FORMAT = 2;
    private static final int ROW_INDEX = 0;
    private static final int COLUMN_INDEX = 1;
    private static final int ROW_LAST_VALUE = 10;

    private final Row row;
    private final Column column;

    private Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public static Position from(String rowColumn) {
        validatePositionLength(rowColumn);
        return new Position(
                new Row(extractRowValue(rowColumn)),
                new Column(extractColumnValue(rowColumn)));
    }

    public static Position of(int row, int column) {
        return new Position(
                new Row(row),
                new Column(column));
    }

    private static int extractColumnValue(String rowColumn) {
        return rowColumn.charAt(COLUMN_INDEX) - '0';
    }

    public Position move(Direction direction) {
        return Position.of(row.getValue() + direction.getRowOffset(),
                column.getValue() + direction.getColOffset());
    }

    public boolean matchesDistance(Position other, int dRow, int dColumn) {
        int diffRowAbs = rowDistanceTo(other);
        int diffColumnAbs = colDistanceTo(other);
        return (diffRowAbs == dRow && diffColumnAbs == dColumn)
                || (diffColumnAbs == dRow && diffRowAbs == dColumn);
    }

    public boolean hasOnlyStraightMove(Position to) {
        return (rowDistanceTo(to) == 0) != (colDistanceTo(to) == 0);
    }

    public int rowDistanceTo(Position other) {
        return Math.abs(other.row.getValue() - this.row.getValue());
    }

    public int colDistanceTo(Position other) {
        return Math.abs(other.column.getValue() - this.column.getValue());
    }

    private static int extractRowValue(String rowColumn) {
        int rowValue = Character.getNumericValue(rowColumn.charAt(ROW_INDEX));
        if (rowValue == 0) {
            return ROW_LAST_VALUE;
        }
        return rowValue;
    }

    public int getRowValue() {
        return row.getValue();
    }

    public int getColumnValue() {
        return column.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(row, position.row) && Objects.equals(column, position.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return row.getValue() + "," + column.getValue();
    }

    private static void validatePositionLength(String rowColumn) {
        if (rowColumn.length() != LENGTH_OF_POSITION_FORMAT) {
            throw new IllegalArgumentException("[ERROR] 좌표값 입력은 2자리 숫자여야 합니다.");
        }
        if (!Character.isDigit(rowColumn.charAt(ROW_INDEX))
                || !Character.isDigit(rowColumn.charAt(COLUMN_INDEX))) {
            throw new IllegalArgumentException("[ERROR] 좌표값은 숫자여야 합니다.");
        }
    }
}
