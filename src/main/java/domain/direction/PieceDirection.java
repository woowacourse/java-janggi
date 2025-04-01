package domain.direction;

import domain.spatial.Vector;
import java.util.List;

public enum PieceDirection {

    KING(new Directions(List.of(
            new Direction(List.of(new Vector(0, 1))),
            new Direction(List.of(new Vector(0, -1))),
            new Direction(List.of(new Vector(1, 0))),
            new Direction(List.of(new Vector(-1, 0)))
    ), false)),
    CHARIOT(new Directions(List.of(
            new Direction(List.of(new Vector(0, 1))),
            new Direction(List.of(new Vector(0, -1))),
            new Direction(List.of(new Vector(1, 0))),
            new Direction(List.of(new Vector(-1, 0)))
    ), true)),
    CANNON(new Directions(List.of(
            new Direction(List.of(new Vector(0, 1))),
            new Direction(List.of(new Vector(0, -1))),
            new Direction(List.of(new Vector(1, 0))),
            new Direction(List.of(new Vector(-1, 0)))
    ), true)),
    HORSE(new Directions(List.of(
            new Direction(List.of(new Vector(0, 1), new Vector(1, 1))),
            new Direction(List.of(new Vector(0, 1), new Vector(-1, 1))),
            new Direction(List.of(new Vector(0, -1), new Vector(1, -1))),
            new Direction(List.of(new Vector(0, -1), new Vector(-1, -1))),
            new Direction(List.of(new Vector(1, 0), new Vector(1, -1))),
            new Direction(List.of(new Vector(1, 0), new Vector(1, 1))),
            new Direction(List.of(new Vector(-1, 0), new Vector(-1, -1))),
            new Direction(List.of(new Vector(-1, 0), new Vector(-1, 1)))
    ), false)),
    ELEPHANT(new Directions(List.of(
            new Direction(List.of(new Vector(0, 1), new Vector(1, 1), new Vector(1, 1))),
            new Direction(List.of(new Vector(0, 1), new Vector(-1, 1), new Vector(-1, 1))),
            new Direction(List.of(new Vector(0, -1), new Vector(1, -1), new Vector(1, -1))),
            new Direction(List.of(new Vector(0, -1), new Vector(-1, -1), new Vector(-1, -1))),
            new Direction(List.of(new Vector(1, 0), new Vector(1, -1), new Vector(1, -1))),
            new Direction(List.of(new Vector(1, 0), new Vector(1, 1), new Vector(1, 1))),
            new Direction(List.of(new Vector(-1, 0), new Vector(-1, -1), new Vector(-1, -1))),
            new Direction(List.of(new Vector(-1, 0), new Vector(-1, 1), new Vector(-1, 1)))
    ), false)),
    GUARD(new Directions(List.of(
            new Direction(List.of(new Vector(0, 1))),
            new Direction(List.of(new Vector(0, -1))),
            new Direction(List.of(new Vector(1, 0))),
            new Direction(List.of(new Vector(-1, 0)))
    ), false)),
    HAN_SOLDIER(new Directions(List.of(
            new Direction(List.of(new Vector(0, 1))),
            new Direction(List.of(new Vector(1, 0))),
            new Direction(List.of(new Vector(-1, 0)))
    ), false)),
    CHO_SOLDIER(new Directions(List.of(
            new Direction(List.of(new Vector(1, 0))),
            new Direction(List.of(new Vector(-1, 0))),
            new Direction(List.of(new Vector(0, -1)))
    ), false)),
    DIAGONAL(new Directions(List.of(
            new Direction(List.of(new Vector(1, 1))),
            new Direction(List.of(new Vector(-1, -1))),
            new Direction(List.of(new Vector(1, -1))),
            new Direction(List.of(new Vector(-1, 1)))
    ), false)),
    REPEATED_DIAGONAL(new Directions(List.of(
            new Direction(List.of(new Vector(1, -1))),
            new Direction(List.of(new Vector(-1, 1))),
            new Direction(List.of(new Vector(1, 1))),
            new Direction(List.of(new Vector(-1, -1)))
    ), true)),
    HAN_DIAGONAL(new Directions(List.of(
            new Direction(List.of(new Vector(1, 1))),
            new Direction(List.of(new Vector(-1, 1)))
    ), false)),
    CHO_DIAGONAL(new Directions(List.of(
            new Direction(List.of(new Vector(1, -1))),
            new Direction(List.of(new Vector(-1, -1)))
    ), false));

    private final Directions directions;

    PieceDirection(final Directions directions) {
        this.directions = directions;
    }

    public Directions get() {
        return directions;
    }
}
