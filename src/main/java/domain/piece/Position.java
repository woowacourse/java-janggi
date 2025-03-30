package domain.piece;

import domain.Move;
import java.util.List;
import java.util.Objects;

public class Position {
    private final int y;
    private final int x;

    public Position(int y, int x) {
        validatePosition(y, x);
        this.y = y;
        this.x = x;
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
        return (y == 1 || y == 8) && x == 4;
    }

    public boolean isPalaceTopRight() {
        return (y == 1 || y == 8) && x == 6;
    }

    public boolean isPalaceBottomLeft() {
        return (y == 3 || y == 10) && x == 4;
    }

    public boolean isPalaceBottomRight() {
        return (y == 3 || y == 10) && x == 6;
    }

    public boolean isPalaceCenter() {
        return (y == 9 && x == 5) || (y == 2 && x == 5);
    }

    public boolean isInPalace() {
        return isInChoPalace(this.y, this.x) || isInHanPalace(this.y, this.x);
    }

    private boolean isInChoPalace(int row, int column) {
        return row >= 8 && row <= 10 && column >= 4 && column <= 6;
    }

    private boolean isInHanPalace(int row, int column) {
        return row >= 1 && row <= 3 && column >= 4 && column <= 6;
    }

    public boolean hasLine(Move move) {
        if (!canApplyMove(move)) {
            return false;
        }
        if (!List.of(Move.BACK_LEFT, Move.BACK_RIGHT, Move.FRONT_LEFT, Move.FRONT_RIGHT).contains(move)) {
            return true;
        }
        if (isPalaceTopLeft() && move == Move.BACK_RIGHT) {
            return true;
        }
        if (isPalaceTopRight() && move == Move.BACK_LEFT) {
            return true;
        }
        if (isPalaceBottomLeft() && move == Move.FRONT_RIGHT) {
            return true;
        }
        if (isPalaceBottomRight() && move == Move.FRONT_LEFT) {
            return true;
        }
        if (isPalaceCenter() && List.of(Move.BACK_LEFT, Move.BACK_RIGHT, Move.FRONT_LEFT, Move.FRONT_RIGHT)
                .contains(move)) {
            return true;
        }
        return false;
    }

    public boolean canMoveInPalace(Move move) {
        if (isInHanPalace(this.y, this.x)) {
            return isInHanPalace(this.y + move.getDy(), this.x + move.getDx());
        }
        return isInChoPalace(this.y + move.getDy(), this.x + move.getDx());
    }

    public Position movePosition(Move move) {
        return new Position(y + move.getDy(), x + move.getDx());
    }

    public boolean canApplyMove(Move move) {
        int movedRow = y + move.getDy();
        int movedColumn = x + move.getDx();
        return isValidPosition(movedRow, movedColumn);
    }

    public int compareRow(Position position) {
        return this.y - position.y;
    }

    public int compareColumn(Position position) {
        return this.x - position.x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
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
        return y == position.y && x == position.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(y, x);
    }
}
