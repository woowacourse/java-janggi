package janggi.position;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Position {
    public static final int COLUMN_MAX = 8;
    public static final int ROW_MAX = 9;

    private final int column;
    private final int row;

    public Position(int column, int row) {
        validate(column, row);
        this.column = column;
        this.row = row;
    }

    public static boolean isCanBePosition(int column, int row) {
        return !(row < 0 || row > ROW_MAX)
                && !(column < 0 || column > COLUMN_MAX);
    }

    private void validate(int column, int row) {
        if (column < 0 || column > COLUMN_MAX) {
            throw new IllegalArgumentException("column이 장기판 범위를 벗어났습니다: " + column);
        }
        if (row < 0 || row > ROW_MAX) {
            throw new IllegalArgumentException("row가 장기판 범위를 벗어났습니다: " + row);
        }
    }

    public boolean isStraightLine(Position opposite) {
        return (this.column == opposite.column || this.row == opposite.row);
    }

    public boolean isSamePoint(Position position) {
        return this.equals(position);
    }

    public boolean canMove(Direction direction) {
        return isCanBePosition(column + direction.getX(), row + direction.getY());
    }

    public boolean isParallel(Position end) {
        return this.row == end.row;
    }

    public List<Position> createParallelPosition(int startColumn, int endColumn) {
        int minColumn = Math.min(startColumn, endColumn);
        int maxColumn = Math.max(startColumn, endColumn);

        return IntStream.range(minColumn, maxColumn + 1)
                .filter(column -> column != this.column)
                .mapToObj(column -> new Position(column, this.row))
                .toList();
    }

    public List<Position> createVerticalPosition(int startRow, int endRow) {
        int minRow = Math.min(startRow, endRow);
        int maxRow = Math.max(startRow, endRow);

        return IntStream.range(minRow, maxRow + 1)
                .filter(row -> row != this.row)
                .mapToObj(row -> new Position(this.column, row))
                .toList();
    }

    public Position move(Direction direction) {
        return new Position(this.column + direction.getX(), this.row + direction.getY());
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Position position = (Position) object;
        return column == position.column && row == position.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }
}
