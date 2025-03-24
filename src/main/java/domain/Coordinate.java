package domain;

import domain.piece.movement.Movement;

public record Coordinate(int row, int col) {

    public static final int MAX_ROW = 10;
    public static final int MAX_COL = 9;

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

}
