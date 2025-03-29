package domain.direction;

import domain.spatial.Vector;
import java.util.List;

public enum PieceDirection {

    KING(new Directions(List.of(
            new Direction(List.of(new Vector(0, 1)), false),
            new Direction(List.of(new Vector(0, -1)), false),
            new Direction(List.of(new Vector(1, 0)), false),
            new Direction(List.of(new Vector(-1, 0)), false)
    ), false)),
    CHARIOT(new Directions(List.of(
            new Direction(List.of(new Vector(0, 1)), false),
            new Direction(List.of(new Vector(0, -1)), false),
            new Direction(List.of(new Vector(1, 0)), false),
            new Direction(List.of(new Vector(-1, 0)), false)
    ), true)),
    CANNON(new Directions(List.of(
            new Direction(List.of(new Vector(0, 1)), false),
            new Direction(List.of(new Vector(0, -1)), false),
            new Direction(List.of(new Vector(1, 0)), false),
            new Direction(List.of(new Vector(-1, 0)), false)
    ), true)),
    HORSE(new Directions(List.of(
            new Direction(List.of(new Vector(0, 1), new Vector(1, 1)), false),
            new Direction(List.of(new Vector(0, 1), new Vector(-1, 1)), false),
            new Direction(List.of(new Vector(0, -1), new Vector(1, -1)), false),
            new Direction(List.of(new Vector(0, -1), new Vector(-1, -1)), false),
            new Direction(List.of(new Vector(1, 0), new Vector(1, -1)), false),
            new Direction(List.of(new Vector(1, 0), new Vector(1, 1)), false),
            new Direction(List.of(new Vector(-1, 0), new Vector(-1, -1)), false),
            new Direction(List.of(new Vector(-1, 0), new Vector(-1, 1)), false)
    ), false)),
    ELEPHANT(new Directions(List.of(
            new Direction(List.of(new Vector(0, 1), new Vector(1, 1), new Vector(1, 1)), false),
            new Direction(List.of(new Vector(0, 1), new Vector(-1, 1), new Vector(-1, 1)), false),
            new Direction(List.of(new Vector(0, -1), new Vector(1, -1), new Vector(1, -1)), false),
            new Direction(List.of(new Vector(0, -1), new Vector(-1, -1), new Vector(-1, -1)), false),
            new Direction(List.of(new Vector(1, 0), new Vector(1, -1), new Vector(1, -1)), false),
            new Direction(List.of(new Vector(1, 0), new Vector(1, 1), new Vector(1, 1)), false),
            new Direction(List.of(new Vector(-1, 0), new Vector(-1, -1), new Vector(-1, -1)), false),
            new Direction(List.of(new Vector(-1, 0), new Vector(-1, 1), new Vector(-1, 1)), false)
    ), false)),
    GUARD(new Directions(List.of(
            new Direction(List.of(new Vector(0, 1)), false),
            new Direction(List.of(new Vector(0, -1)), false),
            new Direction(List.of(new Vector(1, 0)), false),
            new Direction(List.of(new Vector(-1, 0)), false)
    ), false)),
    HAN_SOLDIER(new Directions(List.of(
            new Direction(List.of(new Vector(0, 1)), false),
            new Direction(List.of(new Vector(1, 0)), false),
            new Direction(List.of(new Vector(-1, 0)), false)
    ), false)),
    CHO_SOLDIER(new Directions(List.of(
            new Direction(List.of(new Vector(1, 0)), false),
            new Direction(List.of(new Vector(-1, 0)), false),
            new Direction(List.of(new Vector(0, -1)), false)
    ), false)),
    DIAGONAL(new Directions(List.of(
            new Direction(List.of(new Vector(1, 1)), false),
            new Direction(List.of(new Vector(-1, -1)), false),
            new Direction(List.of(new Vector(1, -1)), false),
            new Direction(List.of(new Vector(-1, 1)), false)
    ), false)),
    HAN_DIAGONAL(new Directions(List.of(
            new Direction(List.of(new Vector(1, 1)), false),
            new Direction(List.of(new Vector(-1, 1)), false)
    ), false)), CHO_DIAGONAL(new Directions(List.of(
            new Direction(List.of(new Vector(1, -1)), false),
            new Direction(List.of(new Vector(-1, -1)), false)
    ), false));

    private final Directions directions;

    PieceDirection(final Directions directions) {
        this.directions = directions;
    }

    public Directions get() {
        return directions;
    }
}
