package domain.piece.movement;

import domain.Coordinate;

public enum ByeongMovement {

    UP(Movement.UP),
    DOWN(Movement.DOWN),
    RIGHT(Movement.RIGHT),
    LEFT(Movement.LEFT);

    private final Movement movement;

    ByeongMovement(Movement movement) {
        this.movement = movement;
    }

    public Coordinate getDirection() {
        return movement.getDirection();
    }

}
