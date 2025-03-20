package janggi.board;

import static janggi.board.Board.ROW_SIZE;

import java.util.Objects;

public class Position {

    private final int column;
    private final int row;

    public Position(int column, int row) {
        validateRange(column, row);
        this.column = column;
        this.row = row;
    }

    private void validateRange(int column, int row) {
        if (column < 0 || column >= Board.COLUMN_SIZE || row < 0 || row >= ROW_SIZE) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 좌표입니다.");
        }
    }

    public Position(String column, String row) {
        this(Integer.parseInt(column), Integer.parseInt(row));
    }

    public Position plus(int columnDifference, int rowDifference) {
        return new Position(column + columnDifference, row + rowDifference);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Position position = (Position) object;
        return column == position.column && row == position.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }

    @Override
    public String toString() {
        return "" + column + row;
    }
}
