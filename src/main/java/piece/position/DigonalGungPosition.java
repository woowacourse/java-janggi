package piece.position;

import java.util.Arrays;

public enum DigonalGungPosition {

    RED_LEFT_DOWN(new JanggiPosition(0, 3)),
    RED_RIGHT_DOWN(new JanggiPosition(0, 5)),
    RED_LEFT_UP(new JanggiPosition(2, 3)),
    RED_RIGHT_UP(new JanggiPosition(2, 5)),

    BLUE_LEFT_DOWN(new JanggiPosition(9, 3)),
    BLUE_RIGHT_DOWN(new JanggiPosition(9, 5)),
    BLUE_LEFT_UP(new JanggiPosition(7, 3)),
    BLUE_RIGHT_UP(new JanggiPosition(7, 5));

    private final JanggiPosition position;

    DigonalGungPosition(JanggiPosition position) {
        this.position = position;
    }

    public static boolean isPositionDiagonalGungPosition(JanggiPosition janggiPosition) {
        return Arrays.stream(DigonalGungPosition.values())
                .anyMatch((digonalGungPosition) -> digonalGungPosition.position.equals(janggiPosition));
    }
}
