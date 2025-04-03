package direction;

import java.util.Objects;

public final class Point {

    private static final int PALACE_START_X = 4;
    private static final int PALACE_END_X = 6;

    private static final int CHO_PALACE_START_Y = 10;
    private static final int CHO_PALACE_END_Y = 8;
    private static final int HAN_PALACE_START_Y = 1;
    private static final int HAN_PALACE_END_Y = 3;

    private static final int PALACE_CENTER_X = 5;

    private static final int CHO_PALACE_CENTER_Y = 9;
    private static final int HAN_PALACE_CENTER_Y = 2;

    private final int column;
    private final int row;

    public Point(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public Point minus(Point other) {
        return new Point(column - other.column, row - other.row);
    }

    public Point plus(Point other) {
        return new Point(column + other.column, row + other.row);
    }

    public boolean isDifferentColumn(Point point) {
        return point.column != this.column;
    }

    public boolean isDifferentRow(Point point) {
        return point.row != this.row;
    }

    public Point move(Movement movement) {
        return new Point(column + movement.getColumn(), row + movement.getRow());
    }

    public int moveCount(Point point) {
        return Math.abs(point.row) + Math.abs(point.column);
    }

    public boolean isPalaceCorner() {
        return (PALACE_START_X == column || PALACE_END_X == column)
                && (CHO_PALACE_END_Y == row || CHO_PALACE_START_Y == row
                || HAN_PALACE_START_Y == row || HAN_PALACE_END_Y == row);
    }

    public boolean isPalace() {
        return PALACE_START_X <= column && PALACE_END_X >= column
                && ((CHO_PALACE_START_Y >= row && CHO_PALACE_END_Y <= row)
                || (HAN_PALACE_START_Y <= row && HAN_PALACE_END_Y >= row));
    }

    public boolean isPalaceCenter() {
        return (PALACE_CENTER_X == column && (CHO_PALACE_CENTER_Y == row || HAN_PALACE_CENTER_Y == row));
    }

    public int column() {
        return column;
    }

    public int row() {
        return row;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Point) obj;
        return this.column == that.column &&
                this.row == that.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }
}
