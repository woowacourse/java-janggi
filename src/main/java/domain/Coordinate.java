package domain;

import domain.piece.movement.Movement;

public record Coordinate(int row, int col) {

    public static final int MAX_ROW = 10;
    public static final int MAX_COL = 9;

    public boolean isInBoundary() {
        return !isOutOfBoundary();
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
        return move(movement.getDirection().row(), movement.getDirection().col());
    }

    public boolean isGungDownRightCorner() {
        return this.equals(new Coordinate(3, 6)) || this.equals(new Coordinate(10, 6));
    }

    public boolean isGungDownLeftCorner() {
        return this.equals(new Coordinate(3, 4)) || this.equals(new Coordinate(10, 4));
    }

    public boolean isGungUpRightCorner() {
        return this.equals(new Coordinate(1, 6)) || this.equals(new Coordinate(8, 6));
    }

    public boolean isGungUpLeftCorner() {
        return this.equals(new Coordinate(1, 4)) || this.equals(new Coordinate(8, 4));
    }

    public boolean isGungCenter() {
        return this.equals(new Coordinate(2, 5)) || this.equals(new Coordinate(9, 5));
    }

    public boolean isInGungBoundary() {
        if (col >= 4 && col <= 6) {
            return (row >= 1 && row <= 3) || (row >= 7 && row <= 9);
        }
        return false;
    }
}
