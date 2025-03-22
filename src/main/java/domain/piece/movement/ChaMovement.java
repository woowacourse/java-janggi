package domain.piece.movement;

import domain.Coordinate;

public enum ChaMovement {

    UP(new Coordinate(-1, 0)),
    DOWN(new Coordinate(1, 0)),
    RIGHT(new Coordinate(0, 1)),
    LEFT(new Coordinate(0, -1));

    private final Coordinate direction;

    ChaMovement(Coordinate direction) {
        this.direction = direction;
    }

    public Coordinate getDirection() {
        return direction;
    }
}
