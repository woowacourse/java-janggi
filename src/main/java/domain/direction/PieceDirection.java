package domain.direction;

import domain.Vector;
import java.util.List;

public enum PieceDirection {

    GENERAL(new Directions(List.of(
            new Direction(List.of(Vector.UP), false),
            new Direction(List.of(Vector.DOWN), false),
            new Direction(List.of(Vector.RIGHT), false),
            new Direction(List.of(Vector.LEFT), false)
    ))),
    CHARIOT(new Directions(List.of(
            new Direction(List.of(Vector.UP), true),
            new Direction(List.of(Vector.DOWN), true),
            new Direction(List.of(Vector.RIGHT), true),
            new Direction(List.of(Vector.LEFT), true)
    ))),
    CANNON(new Directions(List.of(
            new Direction(List.of(Vector.UP), true),
            new Direction(List.of(Vector.DOWN), true),
            new Direction(List.of(Vector.RIGHT), true),
            new Direction(List.of(Vector.LEFT), true)
    ))),
    HORSE(new Directions(List.of(
            new Direction(List.of(Vector.UP, Vector.UP_RIGHT), false),
            new Direction(List.of(Vector.UP, Vector.UP_LEFT), false),
            new Direction(List.of(Vector.DOWN, Vector.DOWN_RIGHT), false),
            new Direction(List.of(Vector.DOWN, Vector.DOWN_LEFT), false),
            new Direction(List.of(Vector.RIGHT, Vector.UP_RIGHT), false),
            new Direction(List.of(Vector.RIGHT, Vector.DOWN_RIGHT), false),
            new Direction(List.of(Vector.LEFT, Vector.UP_LEFT), false),
            new Direction(List.of(Vector.LEFT, Vector.DOWN_LEFT), false)
    ))),
    ELEPHANT(new Directions(List.of(
            new Direction(List.of(Vector.UP, Vector.UP_RIGHT, Vector.UP_RIGHT), false),
            new Direction(List.of(Vector.UP, Vector.UP_LEFT, Vector.UP_LEFT), false),
            new Direction(List.of(Vector.DOWN, Vector.DOWN_RIGHT, Vector.DOWN_RIGHT), false),
            new Direction(List.of(Vector.DOWN, Vector.DOWN_LEFT, Vector.DOWN_LEFT), false),
            new Direction(List.of(Vector.RIGHT, Vector.UP_RIGHT, Vector.UP_RIGHT), false),
            new Direction(List.of(Vector.RIGHT, Vector.DOWN_RIGHT, Vector.DOWN_RIGHT), false),
            new Direction(List.of(Vector.LEFT, Vector.UP_LEFT, Vector.UP_LEFT), false),
            new Direction(List.of(Vector.LEFT, Vector.DOWN_LEFT, Vector.DOWN_LEFT), false)
    ))),
    GUARD(new Directions(List.of(
            new Direction(List.of(Vector.UP), false),
            new Direction(List.of(Vector.DOWN), false),
            new Direction(List.of(Vector.RIGHT), false),
            new Direction(List.of(Vector.LEFT), false)
    ))),
    HAN_SOLDIER(new Directions(List.of(
            new Direction(List.of(Vector.DOWN), false),
            new Direction(List.of(Vector.RIGHT), false),
            new Direction(List.of(Vector.LEFT), false)
    ))),
    CHO_SOLDIER(new Directions(List.of(
            new Direction(List.of(Vector.UP), false),
            new Direction(List.of(Vector.RIGHT), false),
            new Direction(List.of(Vector.LEFT), false)
    ))),
    ;

    private final Directions directions;

    PieceDirection(final Directions directions) {
        this.directions = directions;
    }

    public Directions get() {
        return directions;
    }
}
