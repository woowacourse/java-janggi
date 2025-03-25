package domain;

import java.util.Objects;

public class Position {
    private final int row;
    private final int column;

    public Position(int row, int column) {
        validatePosition(row, column);
        this.row = row;
        this.column = column;
    }

    private void validatePosition(int row, int column) {
        if (!isValidPosition(row, column)) {
            throw new IllegalArgumentException("장기판을 넘은 이동은 불가능 합니다.");
        }
    }

    private boolean isValidPosition(int row, int column) {
        return row >= 1 && row <= 10 && column >= 1 && column <= 9;
    }

    public Position movePosition(Move move) {
        return new Position(row + move.getDy(), column + move.getDx());
    }

    public boolean canApplyMove(Move move) {
        int movedRow = row + move.getDy();
        int movedColumn = column + move.getDx();
        return isValidPosition(movedRow, movedColumn);
    }

    public int compareRow(Position position) {
        return this.row - position.row;
    }

    public int compareColumn(Position position) {
        return this.column - position.column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
