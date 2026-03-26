package domain.piece;

import domain.position.Coordinate;
import domain.position.Position;

import java.util.*;

public enum Direction {

    UP(1, 0),
    UP_RIGHT(1, 1),
    RIGHT(0, 1),
    DOWN_RIGHT(-1, 1),
    DOWN(-1, 0),
    DOWN_LEFT(-1, -1),
    LEFT(0, -1),
    UP_LEFT(1, -1);

    private final int dRow;
    private final int dColumn;

    Direction(int dRow, int dColumn) {
        this.dRow = dRow;
        this.dColumn = dColumn;
    }

    public int getdRow() {
        return dRow;
    }

    public int getdColumn() {
        return dColumn;
    }

    public static Direction from(int row, int col) {
        return Arrays.stream(values())
                .filter(direction -> direction.dRow == row && direction.dColumn == col)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당되는 단위 방향이 아닙니다."));
    }

    public static Queue<Direction> of(Position startPosition, Position endPosition) {
        Coordinate differentCoordinate = endPosition.minus(startPosition);
        int row = differentCoordinate.row();
        int column = differentCoordinate.column();
        int rowAbs = Math.abs(row);
        int columnAbs = Math.abs(column);

        Queue<Direction> directions = new ArrayDeque<>();
        // 절댓값이 큰 값의 실제 값 - 절대 값이 작은 실제값
        if (rowAbs > columnAbs) {
            for (int i = 0; i < Math.abs(rowAbs - columnAbs); i++) {
                directions.add(from(row - column / Math.abs(row - column), 0));
                row = (rowAbs - 1) * (row / rowAbs);
            }
        }
        if (columnAbs > rowAbs) {
            for (int i = 0; i < Math.abs(rowAbs - columnAbs); i++) {
                directions.add(from(0, column - row / Math.abs(column - row)));
                column = (columnAbs - 1) * (column / columnAbs);
            }
        }
        for (int i = 0; i < Math.abs(row); i++) {
            directions.add(from(row / Math.abs(row), column / Math.abs(column)));
        }

        return directions;
    }
}
