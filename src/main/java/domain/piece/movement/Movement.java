package domain.piece.movement;

import domain.Coordinate;

public enum Movement {

    UP(new Coordinate(-1, 0)),
    DOWN(new Coordinate(1, 0)),
    RIGHT(new Coordinate(0, 1)),
    LEFT(new Coordinate(0, -1)),
    UP_RIGHT(new Coordinate(-1, 1)),
    DOWN_RIGHT(new Coordinate(1, 1)),
    DOWN_LEFT(new Coordinate(1, -1)),
    UP_LEFT(new Coordinate(-1, -1));

    private final Coordinate direction;

    Movement(Coordinate direction) {
        this.direction = direction;
    }

    public Coordinate getDirection() {
        return direction;
    }

}
