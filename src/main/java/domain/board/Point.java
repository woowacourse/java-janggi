package domain.board;

import domain.movements.Direction;

public record Point(int row, int column) {

    public Point move(final Direction direction) {
        return new Point(row + direction.getRow(), column + direction.getColumn());
    }

    public boolean isInRange(int maxRow, int maxColumn) {
        return isInRangeOnRow(maxRow) && isInRangeOnColumn(maxColumn);
    }

    private boolean isInRangeOnRow(int maxRow) {
        return row >= 0 && row <= maxRow;
    }

    private boolean isInRangeOnColumn(int maxColumn) {
        return column >= 0 && row <= maxColumn;
    }

    @Override
    public String toString() {
        return "(" + row + "," + column + ")";
    }
}
