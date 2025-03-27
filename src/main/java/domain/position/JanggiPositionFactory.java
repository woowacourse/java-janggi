package domain.position;

import domain.position.castle.*;

import java.util.Arrays;

public enum JanggiPositionFactory {
    RED_LEFT_TOP_CASTLE(new LeftTopCastlePosition(0, 3)),
    RED_LEFT_MIDDLE_CASTLE(new LeftMiddleCastlePosition(1, 3)),
    RED_LEFT_BOTTOM_CASTLE(new LeftBottomCastlePosition(2, 3)),
    RED_MIDDLE_TOP_CASTLE(new MiddleTopCastlePosition(0, 4)),
    RED_CENTER_CASTLE(new CenterCastlePosition(1, 4)),
    RED_MIDDLE_BOTTOM_CASTLE(new MiddleBottomCastlePosition(2, 4)),
    RED_RIGHT_TOP_CASTLE(new RightTopCastlePosition(0, 5)),
    RED_RIGHT_MIDDLE_CASTLE(new RightMiddleCastlePosition(1, 5)),
    RED_RIGHT_BOTTOM_CASTLE(new RightBottomCastlePosition(2, 5)),

    BLUE_LEFT_TOP_CASTLE(new LeftTopCastlePosition(7, 3)),
    BLUE_LEFT_MIDDLE_CASTLE(new LeftMiddleCastlePosition(8, 3)),
    BLUE_LEFT_BOTTOM_CASTLE(new LeftBottomCastlePosition(9, 3)),
    BLUE_MIDDLE_TOP_CASTLE(new MiddleTopCastlePosition(7, 4)),
    BLUE_CENTER_CASTLE(new CenterCastlePosition(8, 4)),
    BLUE_MIDDLE_BOTTOM_CASTLE(new MiddleBottomCastlePosition(9, 4)),
    BLUE_RIGHT_TOP_CASTLE(new RightTopCastlePosition(7, 5)),
    BLUE_RIGHT_MIDDLE_CASTLE(new RightMiddleCastlePosition(8, 5)),
    BLUE_RIGHT_BOTTOM_CASTLE(new RightBottomCastlePosition(9, 5)),
    ;

    private final JanggiPosition position;

    JanggiPositionFactory(JanggiPosition position) {
        this.position = position;
    }

    public static JanggiPosition of(final int row, final int col) {
        return Arrays.stream(values())
                .map(JanggiPositionFactory::getPosition)
                .filter(p -> p.getRow() == row && p.getCol() == col)
                .findFirst()
                .orElse(new NormalPosition(row, col));
    }

    public JanggiPosition getPosition() {
        return position;
    }
}
