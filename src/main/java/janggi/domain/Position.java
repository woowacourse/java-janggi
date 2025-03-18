package janggi.domain;

import janggi.common.ErrorMessage;
import java.util.Objects;

public class Position {

    private int row;
    private int column;

    public Position(int row, int column) {
        validate(row, column);
        this.row = row;
        this.column = column;
    }

    private void validate(int row, int column) {
        if (row <= 0 || row > 10 || column <= 0 || column > 9) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_BOARD_POSITION.getMessage());
        }
    }

    public void update(int row, int column) {
        this.row = row;
        this.column = column;
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
}
