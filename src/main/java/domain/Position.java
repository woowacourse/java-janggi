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

    public boolean isPalaceTopLeft() {
        return (row == 1 || row == 8) && column == 4;
    }

    public boolean isPalaceTopRight() {
        return (row == 1 || row == 8) && column == 6;
    }

    public boolean isPalaceBottomLeft() {
        return (row == 3 || row == 10) && column == 4;
    }

    public boolean isPalaceBottomRight() {
        return (row == 3 || row == 10) && column == 6;
    }

    public boolean isPalaceCenter() {
        return (row == 9 && column == 5) || (row == 2 && column == 5);
    }

    public boolean isInPalace() {
        return checkInPalace(row, column);
    }

    private boolean checkInPalace(int row, int column) {
        return (row >= 1 && row <= 3 && column >= 4 && column <= 6) ||
                (row >= 8 && row <= 10 && column >= 4 && column <= 6);
    }

    public boolean canMoveInPalace(Move move) {
        return checkInPalace(this.row + move.getDy(), this.column + move.getDx());
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
