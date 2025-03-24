package domain.board;

import domain.movements.Direction;

public record Point(int row, int column) {

    public Point move(final Direction direction) {
        return new Point(row + direction.getRow(), column + direction.getColumn());
    }

    @Override
    public String toString() {
        return "(" + row + "," + column + ")";
    }
}
