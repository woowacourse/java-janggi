package domain.position;

import java.util.Objects;

public class Position {
    private static int CHO_PALACE_MIN_ROW = 0;
    private static int CHO_PALACE_MAX_ROW = 2;
    private static int HAN_PALACE_MIN_ROW = 7;
    private static int HAN_PALACE_MAX_ROW = 9;
    private static int PALACE_MIN_COLUMN = 3;
    private static int PALACE_MAX_COLUMN = 5;

    private final Row row;
    private final Column column;

    private Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public static Position of(int row, int column) {
        return new Position(new Row(row), new Column(column));
    }

    public Position movePosition(int deltaRow, int deltaColumn) {
        return new Position(row.moveRow(deltaRow), column.moveColumn(deltaColumn));
    }

    public boolean isInPalace() {
        return isInChoPalace() || isInHanPalace();
    }

    public boolean isInChoPalace() {
        return row.isInRange(CHO_PALACE_MIN_ROW, CHO_PALACE_MAX_ROW) && column.isInRange(PALACE_MIN_COLUMN,
                PALACE_MAX_COLUMN);
    }

    public boolean isInHanPalace() {
        return row.isInRange(HAN_PALACE_MIN_ROW, HAN_PALACE_MAX_ROW) && column.isInRange(PALACE_MIN_COLUMN,
                PALACE_MAX_COLUMN);
    }

    public boolean canMovePosition(int deltaRow, int deltaColumn) {
        return row.canMoveRow(deltaRow) && column.canMoveColumn(deltaColumn);
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

    public int getColumn() {
        return column.getColumn();
    }

    public int getRow() {
        return row.getRow();
    }
}
