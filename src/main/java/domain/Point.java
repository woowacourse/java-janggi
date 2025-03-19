package domain;

import domain.piece.Direction;

public record Point(int row, int column) {

    public Point move(Direction direction) {
        return new Point(row + direction.getRow(), column + direction.getColumn());
    }
}
