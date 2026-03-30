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

    public static Position from(int rowValue, int colValue) {
        return new Position(new Row(rowValue), new Column(colValue));
    }

    public static Position from(String rowColumn) {
        validatePositionLength(rowColumn);
        return new Position(new Row(extractRowValue(rowColumn)), new Column(extractColumnValue(rowColumn)));
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

    private static void validatePositionLength(String rowColumn) {
        if (rowColumn.length() != LENGTH_OF_POSITION_FORMAT) {
            throw new IllegalArgumentException("[ERROR] 좌표값 입력은 2자리 숫자여야 합니다.");
        }
    }

    public boolean hasOffsetPairs(Position other, int value1, int value2) {
        int diffRowAbs = Math.abs(other.getRowValue() - this.getRowValue());
        int diffColumnAbs = Math.abs(other.getColumnValue() - this.getColumnValue());

        return (diffRowAbs == value1 && diffColumnAbs == value2)
                || (diffColumnAbs == value1 && diffRowAbs == value2);
    }

    public boolean hasOnlyStraightMove(Position to) {
        int diffRowAbs = Math.abs(to.getRowValue() - this.getRowValue());
        int diffColumnAbs = Math.abs(to.getColumnValue() - this.getColumnValue());

        return (diffRowAbs == 0) != (diffColumnAbs == 0);
    }

    public Position moveStraight(Position to) {
        int diffRow = to.getRowValue() - getRowValue();
        int diffColumn = to.getColumnValue() - getColumnValue();

        if (Math.abs(diffRow) > Math.abs(diffColumn)) {
            return Position.from(getRowValue() + toUnit(diffRow), getColumnValue());
        }
        return Position.from(getRowValue(), getColumnValue() + toUnit(diffColumn));
    }

    // 대각선 이동: 양 축 모두 1칸씩
    public Position moveDiagonal(Position to) {
        int unitRow = toUnit(to.getRowValue() - getRowValue());
        int unitColumn = toUnit(to.getColumnValue() - getColumnValue());

        return Position.from(getRowValue() + unitRow, getColumnValue() + unitColumn);
    }

    private int toUnit(int diff) {
        if (diff == 0) {
            return 0;
        }
        return diff / Math.abs(diff);
    }

    public int getRowValue() {
        return row.getValue();
    }

    public int getColumnValue() {
        return column.getValue();
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
        return row.getValue() + "," + column.getValue();
    }
}
