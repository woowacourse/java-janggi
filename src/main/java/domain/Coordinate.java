package domain;

import domain.piece.movement.Movement;
import java.util.Objects;

public final class Coordinate {

    public static final int MAX_ROW = 10;
    public static final int MAX_COL = 9;

    private final int row;
    private final int col;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isOutOfBoundary() {
        if (this.row < 1 || this.row > MAX_ROW) {
            return true;
        }
        return this.col < 1 || this.col > MAX_COL;
    }

    public Coordinate move(int increaseRow, int increaseCol) {
        return new Coordinate(row + increaseRow, col + increaseCol);
    }

    public Coordinate move(Movement movement) {
        return move(movement.getDirection().getRow(), movement.getDirection().getCol());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordinate that = (Coordinate) o;
        return row == that.row && col == that.col;
    }

    public int hashCode() {
        return Objects.hash(row, col);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
