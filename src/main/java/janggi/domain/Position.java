package janggi.domain;

import janggi.common.ErrorMessage;
import janggi.domain.piece.Vector;
import java.util.Objects;
import java.util.Optional;

public class Position {

    private int row;
    private int column;

    public Position(int row, int column) {
        validate(row, column);
        this.row = row;
        this.column = column;
    }

    private void validate(int row, int column) {
        if (!isValid(row, column)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_BOARD_POSITION.getMessage());
        }
    }

    private boolean isValid(int row, int column) {
        return row > 0 && row <= 10 && column > 0 && column <= 9;
    }

    public void update(Position newPosition) {
        update(newPosition.row, newPosition.column);
    }

    public void update(int row, int column) {
        validate(row, column);
        this.row = row;
        this.column = column;
    }

    public Position diff(Position comparePosition) {
        return new Position(comparePosition.row - this.row, comparePosition.column - this.column);
    }

    public Optional<Position> calculate(Vector vector) {
        int newRow = this.row + vector.y();
        int newColumn = this.column + vector.x();

        if (isValid(newRow, newColumn)) {
            return Optional.of(new Position(newRow, newColumn));
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
        return "Position{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
