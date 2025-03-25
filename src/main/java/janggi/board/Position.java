package janggi.board;

import java.util.Objects;

public class Position {

    private final int column;
    private final int row;

    public Position(int column, int row) {
        validateColumnRange(column);
        validateRowRange(row);
        this.column = column;
        this.row = row;
    }

    public Position(String column, String row) {
        this(Integer.parseInt(column), Integer.parseInt(row));
    }

    public int calculatesRowDifference(Position other) {
        return other.getRow() - row;
    }

    public int calculatesColumnDifference(Position other) {
        return other.getColumn() - column;
    }

    public Position modify(int columnDistance, int rowDistance) {
        return new Position(column + columnDistance, row + rowDistance);
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

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    private void validateColumnRange(int column) {
        if (column < 0 || column > 8) {
            throw new IllegalArgumentException("[ERROR] 좌표의 Column이 제한 범위를 벗어났습니다.");
        }
    }

    private void validateRowRange(int row) {
        if (row < 0 || row > 9) {
            throw new IllegalArgumentException("[ERROR] 좌표의 Column이 제한 범위를 벗어났습니다.");
        }
    }
}
