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

    private Position(final int row, final int column) {
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

    public boolean isInPalace() {
        List<Position> palacePositions = List.of(
                Position.of(3, 7), Position.of(3, 8), Position.of(3, 9),
                Position.of(4, 7), Position.of(4, 8), Position.of(4, 9),
                Position.of(5, 7), Position.of(5, 8), Position.of(5, 9),

                Position.of(3, 0), Position.of(3, 1), Position.of(3, 2),
                Position.of(4, 0), Position.of(4, 1), Position.of(4, 2),
                Position.of(5, 0), Position.of(5, 1), Position.of(5, 2)
        );
        return palacePositions.contains(this);
    }

    public boolean isInHanPalaceDiagonal() {
        List<Position> hanPalacePositions = List.of(
                Position.of(3, 9), Position.of(4, 8),
                Position.of(5, 7), Position.of(3, 7),
                Position.of(4, 8), Position.of(5, 9)
        );

        return hanPalacePositions.contains(this);
    }

    public boolean isInChoPalaceDiagonal() {
        List<Position> choPalacePositions = List.of(
                Position.of(3, 2), Position.of(4, 1),
                Position.of(5, 0), Position.of(3, 0),
                Position.of(4, 1), Position.of(5, 2)
        );

        return choPalacePositions.contains(this);
    }

    public boolean isCenterOfHanPalace() {
        return this == Position.of(4, 8);
    }

    public boolean isCenterOfChoPalace() {
        return this == Position.of(4, 1);
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
