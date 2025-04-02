package domain.piece.movement;

import domain.piece.coordiante.Coordinate;

public enum Movement {

    UP(new Coordinate(-1, 0)),
    DOWN(new Coordinate(1, 0)),
    RIGHT(new Coordinate(0, 1)),
    LEFT(new Coordinate(0, -1)),
    UP_RIGHT(new Coordinate(-1, 1)),
    DOWN_RIGHT(new Coordinate(1, 1)),
    DOWN_LEFT(new Coordinate(1, -1)),
    UP_LEFT(new Coordinate(-1, -1)),

    UP_UP_RIGHT(new Coordinate(-2, 1)),
    UP_RIGHT_RIGHT(new Coordinate(-1, 2)),
    DOWN_RIGHT_RIGHT(new Coordinate(1, 2)),
    DOWN_DOWN_RIGHT(new Coordinate(2, 1)),
    DOWN_DOWN_LEFT(new Coordinate(2, -1)),
    DOWN_LEFT_LEFT(new Coordinate(1, -2)),
    UP_LEFT_LEFT(new Coordinate(-1, -2)),
    UP_UP_LEFT(new Coordinate(-2, -1)),

    UP_UP_UP_RIGHT_RIGHT(new Coordinate(-3, 2)),
    UP_UP_RIGHT_RIGHT_RIGHT(new Coordinate(-2, 3)),
    DOWN_DOWN_RIGHT_RIGHT_RIGHT(new Coordinate(2, 3)),
    DOWN_DOWN_DOWN_RIGHT_RIGHT(new Coordinate(3, 2)),
    DOWN_DOWN_DOWN_LEFT_LEFT(new Coordinate(3, -2)),
    DOWN_DOWN_LEFT_LEFT_LEFT(new Coordinate(2, -3)),
    UP_UP_LEFT_LEFT_LEFT(new Coordinate(-2, -3)),
    UP_UP_UP_LEFT_LEFT(new Coordinate(-3, -2));

    private final Coordinate direction;

    Movement(Coordinate direction) {
        this.direction = direction;
    }

    public int getRow() {
        return direction.row();
    }

    public int getCol() {
        return direction.col();
    }

    public boolean isDiagonal() {
        return this == UP_RIGHT || this == DOWN_RIGHT || this == DOWN_LEFT || this == UP_LEFT;
    }
}
