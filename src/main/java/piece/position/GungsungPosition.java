package piece.position;

import java.util.Arrays;

public enum GungsungPosition {

    RED_LEFT_DOWN(new JanggiPosition(0, 3), true),
    RED_RIGHT_DOWN(new JanggiPosition(0, 5), true),
    RED_CENTER(new JanggiPosition(1, 4), true),
    RED_LEFT_UP(new JanggiPosition(2, 3), true),
    RED_RIGHT_UP(new JanggiPosition(2, 5), true),

    RED_LEFT(new JanggiPosition(1, 3), false),
    RED_RIGHT(new JanggiPosition(1, 5), false),
    RED_UP(new JanggiPosition(2, 4), false),
    RED_DOWN(new JanggiPosition(0, 4), false),


    BLUE_LEFT_DOWN(new JanggiPosition(9, 3), true),
    BLUE_RIGHT_DOWN(new JanggiPosition(9, 5), true),
    BLUE_CENTER(new JanggiPosition(8, 4), true),
    BLUE_LEFT_UP(new JanggiPosition(7, 3), true),
    BLUE_RIGHT_UP(new JanggiPosition(7, 5), true),

    BLUE_LEFT(new JanggiPosition(8, 3), false),
    BLUE_RIGHT(new JanggiPosition(8, 5), false),
    BLUE_UP(new JanggiPosition(9, 4), false),
    BLUE_DOWN(new JanggiPosition(7, 4), false),
    ;


    private final JanggiPosition position;
    private final boolean isCanMoveDiagonal;

    GungsungPosition(JanggiPosition position, boolean isCanMoveDiagonal) {
        this.position = position;
        this.isCanMoveDiagonal = isCanMoveDiagonal;
    }

    public static boolean isPositionDiagonalGungPosition(JanggiPosition janggiPosition) {
        return Arrays.stream(GungsungPosition.values())
                .filter((gungsungPosition) -> gungsungPosition.isCanMoveDiagonal)
                .anyMatch((gungsungPosition) -> gungsungPosition.position.equals(janggiPosition));
    }

    public static boolean isInsideGungsung(JanggiPosition janggiPosition) {
        return Arrays.stream(GungsungPosition.values())
                .anyMatch((gungsungPosition) -> gungsungPosition.position.equals(janggiPosition));
    }
}
