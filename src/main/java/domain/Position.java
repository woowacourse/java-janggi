package domain;

import domain.move.Move;
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
        if (row < 1 || column < 1 || row > 10 || column > 9) {
            throw new IllegalArgumentException("장기판을 넘은 이동은 불가능 합니다.");
        }
    }

    public Position movePosition(Move move) {
        return new Position(row + move.getDy(), column + move.getDx());
    }

    public boolean canMovePosition(Move move) {
        int movedRow = row + move.getDy();
        int movedColumn = column + move.getDx();
        if (movedRow < 1 || movedColumn < 1 || movedRow > 10 || movedColumn > 9) {
            return false;
        }
        return true;
    }

    public int compareRow(Position targetPosition) {
        return this.row - targetPosition.row;
    }

    public int compareColumn(Position targetPosition) {
        return this.column - targetPosition.column;
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

    public boolean isInsidePalace(Team team) {
        if (team == Team.RED) {
            return row >= 3 && row <= 5 && column >= 4 && column <= 6;
        }
        return row >= 8 && row <= 10 && column >= 4 && column <= 6;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
