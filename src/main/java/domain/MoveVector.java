package domain;

import java.util.Set;

public record MoveVector(
    int deltaX,
    int deltaY
) {

    public static final MoveVector LEFT = new MoveVector(-1, 0);
    public static final MoveVector RIGHT = new MoveVector(1, 0);
    public static final MoveVector UP = new MoveVector(0, -1);
    public static final MoveVector DOWN = new MoveVector(0, 1);

    public static final MoveVector LEFT_UP = new MoveVector(-1, -1);
    public static final MoveVector RIGHT_UP = new MoveVector(1, -1);
    public static final MoveVector LEFT_DOWN = new MoveVector(-1, 1);
    public static final MoveVector RIGHT_DOWN = new MoveVector(1, 1);

    public static final Set<MoveVector> CROSS_MOVE_VECTORS = Set.of(LEFT, RIGHT, UP, DOWN);

    public boolean isUpDirection() {
        return deltaY < 0;
    }

    public boolean isDownDirection() {
        return deltaY > 0;
    }

    public static MoveVector combine(MoveVector... moveVectors) {
        int deltaX = 0;
        int deltaY = 0;
        for (final var movement : moveVectors) {
            deltaX += movement.deltaX;
            deltaY += movement.deltaY;
        }

        return new MoveVector(deltaX, deltaY);
    }
}
