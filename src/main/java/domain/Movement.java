package domain;

import java.util.Set;

public record Movement(
    int deltaX,
    int deltaY
) {

    public static final Movement LEFT = new Movement(-1, 0);
    public static final Movement RIGHT = new Movement(1, 0);
    public static final Movement UP = new Movement(0, -1);
    public static final Movement DOWN = new Movement(0, 1);

    public static final Movement LEFT_UP = new Movement(-1, -1);
    public static final Movement RIGHT_UP = new Movement(1, -1);
    public static final Movement LEFT_DOWN = new Movement(-1, 1);
    public static final Movement RIGHT_DOWN = new Movement(1, 1);

    public static final Set<Movement> CROSS_MOVEMENTS = Set.of(LEFT, RIGHT, UP, DOWN);

    public static Movement combine(Movement... movements) {
        int deltaX = 0;
        int deltaY = 0;
        for (final var movement : movements) {
            deltaX += movement.deltaX;
            deltaY += movement.deltaY;
        }

        return new Movement(deltaX, deltaY);
    }
}
