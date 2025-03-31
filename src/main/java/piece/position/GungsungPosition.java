package piece.position;

import java.util.Arrays;

public enum GungsungPosition {

    RED_ROOM_ONE(new JanggiPosition(0, 3), true),
    RED_ROOM_TWO(new JanggiPosition(0, 5), true),
    RED_ROOM_THREE(new JanggiPosition(1, 4), true),
    RED_ROOM_FOUR(new JanggiPosition(2, 3), true),
    RED_ROOM_FIVE(new JanggiPosition(2, 5), true),

    RED_ROOM_SIX(new JanggiPosition(1, 3), false),
    RED_ROOM_SEVEN(new JanggiPosition(1, 5), false),
    RED_ROOM_EIGHT(new JanggiPosition(2, 4), false),
    RED_ROOM_NINE(new JanggiPosition(0, 4), false),

    BLUE_ROOM_ONE(new JanggiPosition(9, 3), true),
    BLUE_ROOM_TWO(new JanggiPosition(9, 5), true),
    BLUE_ROOM_THREE(new JanggiPosition(8, 4), true),
    BLUE_ROOM_FOUR(new JanggiPosition(7, 3), true),
    BLUE_ROOM_FIVE(new JanggiPosition(7, 5), true),

    BLUE_ROOM_SIX(new JanggiPosition(8, 3), false),
    BLUE_ROOM_SEVEN(new JanggiPosition(8, 5), false),
    BLUE_ROOM_EIGHT(new JanggiPosition(9, 4), false),
    BLUE_ROOM_NINE(new JanggiPosition(7, 4), false);


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
