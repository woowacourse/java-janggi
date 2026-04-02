package domain.board;

import domain.position.Position;
import domain.position.PositionDelta;

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

    private final int dRow;
    private final int dColumn;
    private final boolean isStraight;

    Direction(int dRow, int dColumn, boolean isStraight) {
        this.dRow = dRow;
        this.dColumn = dColumn;
        this.isStraight = isStraight;
    }

    public static Direction from(int row, int col) {
        return Arrays.stream(values())
                .filter(direction -> direction.dRow == row && direction.dColumn == col)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당되는 단위 방향이 아닙니다."));
    }

    public static Queue<Direction> of(Position startPosition, Position endPosition) {
        PositionDelta differentPosition = endPosition.minus(startPosition);
        return calculateDirections(differentPosition.row(), differentPosition.column());
    }

    public boolean isStraight() {
        return isStraight;
    }

    public boolean isDiagonal() {
        return !isStraight;
    }

    public int getdRow() {
        return dRow;
    }

    public int getdColumn() {
        return dColumn;
    }

    public boolean isSameAtLeastOne(Direction direction) {
        return isDiagonal() && (dRow == direction.dRow || dColumn == direction.dColumn);
    }

    public boolean isSameDirection(Direction direction) {
        return this == direction;
    }

    private static Queue<Direction> calculateDirections(int row, int column) {
        Queue<Direction> directions = new ArrayDeque<>();

        int rowAbs = Math.abs(row);
        int columnAbs = Math.abs(column);
        int rowSign = Integer.signum(row);
        int columnSign = Integer.signum(column);
        int diagonalCount = Math.min(rowAbs, columnAbs);
        int straightRowCount = rowAbs - diagonalCount;
        int straightColumnCount = columnAbs - diagonalCount;

        addStraightRowDirections(straightRowCount, directions, rowSign);
        addStraightColumnDirections(straightColumnCount, directions, columnSign);
        addDiagonalDirections(diagonalCount, directions, rowSign, columnSign);

        return directions;
    }

    private static void addDiagonalDirections(int diagonalCount, Queue<Direction> directions, int rowSign, int columnSign) {
        for (int i = 0; i < diagonalCount; i++) {
            directions.add(from(rowSign, columnSign));
        }
    }

    private static void addStraightColumnDirections(int straightColumnCount, Queue<Direction> directions, int columnSign) {
        addDiagonalDirections(straightColumnCount, directions, 0, columnSign);
    }

    private static void addStraightRowDirections(int straightRowCount, Queue<Direction> directions, int rowSign) {
        for (int i = 0; i < straightRowCount; i++) {
            directions.add(from(rowSign, 0));
        }
    }
}
