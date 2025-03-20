package domain.direction;

import domain.piece.Position;
import java.util.List;

public enum PieceDirection {

    KING(new Directions(List.of(
            new Direction(List.of(Position.ofDirection(0, 1)), false),
            new Direction(List.of(Position.ofDirection(0, -1)), false),
            new Direction(List.of(Position.ofDirection(1, 0)), false),
            new Direction(List.of(Position.ofDirection(-1, 0)), false)
    ))),
    ROOK(new Directions(List.of(
            new Direction(List.of(Position.ofDirection(0, 1)), true),
            new Direction(List.of(Position.ofDirection(0, -1)), true),
            new Direction(List.of(Position.ofDirection(1, 0)), true),
            new Direction(List.of(Position.ofDirection(-1, 0)), true)
    ))),
    CANNON(new Directions(List.of(
            new Direction(List.of(Position.ofDirection(0, 1)), true),
            new Direction(List.of(Position.ofDirection(0, -1)), true),
            new Direction(List.of(Position.ofDirection(1, 0)), true),
            new Direction(List.of(Position.ofDirection(-1, 0)), true)
    ))),
    HORSE(new Directions(List.of(
            new Direction(List.of(Position.ofDirection(0, 1), Position.ofDirection(1, 1)), false),
            new Direction(List.of(Position.ofDirection(0, 1), Position.ofDirection(-1, 1)), false),
            new Direction(List.of(Position.ofDirection(0, -1), Position.ofDirection(1, -1)), false),
            new Direction(List.of(Position.ofDirection(0, -1), Position.ofDirection(-1, -1)), false),
            new Direction(List.of(Position.ofDirection(1, 0), Position.ofDirection(1, -1)), false),
            new Direction(List.of(Position.ofDirection(1, 0), Position.ofDirection(1, 1)), false),
            new Direction(List.of(Position.ofDirection(-1, 0), Position.ofDirection(-1, -1)), false),
            new Direction(List.of(Position.ofDirection(-1, 0), Position.ofDirection(-1, 1)), false)
    ))),
    ELEPHANT(new Directions(List.of(
            new Direction(List.of(Position.ofDirection(0, 1), Position.ofDirection(1, 1), Position.ofDirection(1, 1)),
                    false),
            new Direction(List.of(Position.ofDirection(0, 1), Position.ofDirection(-1, 1), Position.ofDirection(-1, 1)),
                    false),
            new Direction(
                    List.of(Position.ofDirection(0, -1), Position.ofDirection(1, -1), Position.ofDirection(1, -1)),
                    false),
            new Direction(
                    List.of(Position.ofDirection(0, -1), Position.ofDirection(-1, -1), Position.ofDirection(-1, -1)),
                    false),
            new Direction(List.of(Position.ofDirection(1, 0), Position.ofDirection(1, -1), Position.ofDirection(1, -1)),
                    false),
            new Direction(List.of(Position.ofDirection(1, 0), Position.ofDirection(1, 1), Position.ofDirection(1, 1)),
                    false),
            new Direction(
                    List.of(Position.ofDirection(-1, 0), Position.ofDirection(-1, -1), Position.ofDirection(-1, -1)),
                    false),
            new Direction(
                    List.of(Position.ofDirection(-1, 0), Position.ofDirection(-1, 1), Position.ofDirection(-1, 1)),
                    false)
    ))),
    ADVISOR(new Directions(List.of(
            new Direction(List.of(Position.ofDirection(0, 1)), false),
            new Direction(List.of(Position.ofDirection(0, -1)), false),
            new Direction(List.of(Position.ofDirection(1, 0)), false),
            new Direction(List.of(Position.ofDirection(-1, 0)), false)
    ))),
    HAN_PAWN(new Directions(List.of(
            new Direction(List.of(Position.ofDirection(0, 1)), false),
            new Direction(List.of(Position.ofDirection(1, 0)), false),
            new Direction(List.of(Position.ofDirection(-1, 0)), false)
    ))),
    CHO_PAWN(new Directions(List.of(
            new Direction(List.of(Position.ofDirection(1, 0)), false),
            new Direction(List.of(Position.ofDirection(-1, 0)), false),
            new Direction(List.of(Position.ofDirection(0, -1)), false)
    ))),
    ;

    private final Directions directions;

    PieceDirection(Directions directions) {
        this.directions = directions;
    }

    public Directions get() {
        return directions;
    }
}
