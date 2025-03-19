package janggi.domain;

import janggi.common.ErrorMessage;
import java.util.Objects;
import java.util.Optional;

public class Position {

    private static final Position[][] CACHED = new Position[11][10];

    static {
        for (int row = 1; row <= 10; row++) {
            for (int column = 1; column <= 9; column++) {
                CACHED[row][column] = new Position(row, column);
            }
        }
    }

    private final int row;
    private final int column;

    private Position(int row, int column) {
        validate(row, column);
        this.row = row;
        this.column = column;
    }

    private static void validate(int row, int column) {
        if (!isValid(row, column)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_BOARD_POSITION.getMessage());
        }
    }

    private static boolean isValid(int row, int column) {
        return row > 0 && row <= 10 && column > 0 && column <= 9;
    }

    public static Position of(int row, int column) {
        validate(row, column);
        return CACHED[row][column];
    }

    public Position update(Position newPosition) {
        return update(newPosition.row, newPosition.column);
    }

    public Position update(int row, int column) {
        validate(row, column);
        return Position.of(row, column);
    }

    public Position diff(Position comparePosition) {
        return Position.of(comparePosition.row - this.row, comparePosition.column - this.column);
    }

    public Optional<Position> calculate(Vector vector) {
        int newRow = this.row + vector.y();
        int newColumn = this.column + vector.x();

        if (isValid(newRow, newColumn)) {
            return Optional.of(Position.of(newRow, newColumn));
        }
        return Optional.empty();
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public String toString() {
        return "Position{" + "row=" + row + ", column=" + column + '}';
    }
}
