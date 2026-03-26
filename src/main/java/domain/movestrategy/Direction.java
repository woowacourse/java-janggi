package domain.movestrategy;

import domain.piece.Position;
import java.util.List;

public enum Direction {
    UP(Position.of(-1, 0)),
    RIGHT_UP(Position.of(-1, 1)),
    RIGHT(Position.of(0, 1)),
    RIGHT_DOWN(Position.of(1, 1)),
    DOWN(Position.of(1, 0)),
    LEFT_DOWN(Position.of(1, -1)),
    LEFT(Position.of(0, -1)),
    LEFT_UP(Position.of(-1, -1));

    private final Position delta;

    Direction(final Position delta) {
        this.delta = delta;
    }

    public static List<Direction> getVerticalAndHorizontal() {
        return List.of(UP, RIGHT, DOWN, LEFT);
    }

    public Position getDelta() {
        return delta;
    }
}
