package janggi.domain.position;

import java.util.Objects;

public class Position {

    private static final int LENGTH_OF_POSITION_FORMAT = 2;
    private static final int ROW_INDEX = 0;
    private static final int COLUMN_INDEX = 1;

    private final Row row;
    private final Column column;

    private Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public static Position of(int rowValue, int colValue) {
        return new Position(new Row(rowValue), new Column(colValue));
    }

    public static Position from(String rowColumn) {
        validatePositionLength(rowColumn);
        return new Position(new Row(extractRowValue(rowColumn)), new Column(extractColumnValue(rowColumn)));
    }

    public Position nextStraight(Position to) {
        int diffRow = calculateRowDiff(to);
        int diffColumn = calculateColumnDiff(to);

        if (Math.abs(diffRow) > Math.abs(diffColumn)) {
            return Position.of(getRowValue() + Integer.signum(diffRow), getColumnValue());
        }
        return Position.of(getRowValue(), getColumnValue() + Integer.signum(diffColumn));
    }

    public Position nextDiagonal(Position to) {
        int unitRow = Integer.signum(calculateRowDiff(to));
        int unitColumn = Integer.signum(calculateColumnDiff(to));

        return Position.of(getRowValue() + unitRow, getColumnValue() + unitColumn);
    }

    public int getRowValue() {
        return row.getValue();
    }

    public int getColumnValue() {
        return column.getValue();
    }

    private static void validatePositionLength(String rowColumn) {
        if (rowColumn.length() != LENGTH_OF_POSITION_FORMAT) {
            throw new IllegalArgumentException("[ERROR] 올바른 좌표값이 아닙니다.");
        }
    }

    private static int extractRowValue(String rowColumn) {
        int rowValue = rowColumn.charAt(ROW_INDEX) - '0';
        if (rowValue == 0) {
            rowValue += 10;
        }
        return rowValue;
    }

    private static int extractColumnValue(String rowColumn) {
        return rowColumn.charAt(COLUMN_INDEX) - '0';
    }

    private int calculateRowDiff(Position other) {
        return other.getRowValue() - getRowValue();
    }

    private int calculateColumnDiff(Position other) {
        return other.getColumnValue() - getColumnValue();
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

    @Override
    public String toString() {
        return row.toString() + column.toString();
    }
}
