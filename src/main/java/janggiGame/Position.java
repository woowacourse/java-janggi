package janggiGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Position {
    private static final int MIN_ROW_RANGE = 0;
    private static final int MAX_ROW_RANGE = 8;
    private static final int MIN_COLUMN_RANGE = 0;
    private static final int MAX_COLUMN_RANGE = 9;
    private static final List<Position> POSITIONS = createDots();

    private final int row;
    private final int column;

    public Position(final int row, final int column) {
        this.row = row;
        this.column = column;
    }

    private static List<Position> createDots() {
        List<Position> positions = new ArrayList<>();
        for (int column = MAX_COLUMN_RANGE; column >= MIN_COLUMN_RANGE; column--) {
            for (int row = MIN_ROW_RANGE; row <= MAX_ROW_RANGE; row++) {
                positions.add(new Position(row, column));
            }
        }
        return positions;
    }

    public static Position of(final int row, final int column) {
        return POSITIONS.stream()
                .filter(d -> d.row == row && d.column == column)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 장기판에 존재하는 좌표가 아닙니다."));
    }

    public static List<Position> getDots() {
        return List.copyOf(POSITIONS);
    }

    public Position getReverse() {
        return POSITIONS.stream()
                .filter(d -> d.row == MAX_ROW_RANGE - this.row)
                .filter(d -> d.column == MAX_COLUMN_RANGE - this.column)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }

    public int calculateRowChange(Position other) {
        return other.row - this.row;
    }

    public int calculateColumnChange(Position other) {
        return other.column - this.column;
    }

    public Position up() {
        return of(row, column + 1);
    }

    public Position down() {
        return of(row, column - 1);
    }

    public Position right() {
        return of(row + 1, column);
    }

    public Position left() {
        return of(row - 1, column);
    }

    public Position upRight() {
        return of(row + 1, column + 1);
    }

    public Position upLeft() {
        return of(row - 1, column + 1);
    }

    public Position downRight() {
        return of(row + 1, column - 1);
    }

    public Position downLeft() {
        return of(row - 1, column - 1);
    }

    @Override
    public boolean equals(Object o) {
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
}
