package domain.board;

import domain.position.Coordinate;
import domain.position.Position;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public enum Direction {

    UP(1, 0, true),
    UP_RIGHT(1, 1, false),
    RIGHT(0, 1, true),
    DOWN_RIGHT(-1, 1, false),
    DOWN(-1, 0, true),
    DOWN_LEFT(-1, -1, false),
    LEFT(0, -1, true),
    UP_LEFT(1, -1, false);

    private final int deltaRow;
    private final int deltaColumn;
    private final boolean isStraight;

    Direction(int deltaRow, int deltaColumn, boolean isStraight) {
        this.deltaRow = deltaRow;
        this.deltaColumn = deltaColumn;
        this.isStraight = isStraight;
    }

    public static Direction from(int row, int col) {
        return Arrays.stream(values())
                .filter(direction -> direction.deltaRow == row && direction.deltaColumn == col)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당되는 단위 방향이 아닙니다."));
    }

    public static Queue<Direction> of(Position startPosition, Position endPosition) {
        Coordinate differentCoordinate = endPosition.minus(startPosition);
        int row = differentCoordinate.row();
        int column = differentCoordinate.column();
        return calculateDirections(row, column);
    }

    private static Queue<Direction> calculateDirections(int row, int column) {
        Queue<Direction> directions = new ArrayDeque<>();
        // 절댓값이 큰 값의 실제 값 - 절대 값이 작은 실제값
        if (Math.abs(row) > Math.abs(column)) {
            row = updateRowBasedOnDirection(Math.abs(row), directions, row, column);
        }
        if (Math.abs(column) > Math.abs(row)) {
            column = updateColumnBasedOnDirection(Math.abs(column), directions, row, column);
        }
        addDiagonalDirection(directions, row, column);
        return directions;
    }

    private static int updateRowBasedOnDirection(int rowAbs, Queue<Direction> directions, int row, int column) {
        for (int i = rowAbs; i > Math.abs(column); i--) {
            directions.add(from((row - column) / Math.abs(row - column), 0));
            row = (i - 1) * (row / i);
        }
        return row;
    }

    private static int updateColumnBasedOnDirection(int columnAbs, Queue<Direction> directions, int row, int column) {
        for (int i = columnAbs; i > Math.abs(row); i--) {
            directions.add(from(0, (column - row) / Math.abs(column - row)));
            column = (i - 1) * (column / i);
        }
        return column;
    }

    private static void addDiagonalDirection(Queue<Direction> directions, int row, int column) {
        if (row == 0 || column == 0) return;
        for (int i = 0; i < Math.abs(row); i++) {
            directions.add(from(row / Math.abs(row), column / Math.abs(column)));
        }
    }

    public boolean isStraight() {
        return isStraight;
    }

    public boolean isDiagonal() {
        return !isStraight;
    }

    public boolean isDownForward() {
        return deltaRow == -1;
    }

    public int getDeltaRow() {
        return deltaRow;
    }

    public int getDeltaColumn() {
        return deltaColumn;
    }

    public boolean isNotSameAtLeastOne(Direction direction) {
        return (deltaRow != direction.deltaRow && deltaColumn != direction.deltaColumn);
    }
}
