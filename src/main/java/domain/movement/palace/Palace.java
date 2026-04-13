package domain.movement.palace;

import domain.board.Column;
import domain.board.Position;
import domain.board.Row;

public class Palace {
    private static final Position HAN_TOP_LEFT = new Position(Column.D, Row.ZERO);
    private static final Position HAN_BOTTOM_RIGHT = new Position(Column.F, Row.TWO);
    private static final Position HAN_CENTER = new Position(Column.E, Row.ONE);

    private static final Position CHO_TOP_LEFT = new Position(Column.D, Row.SEVEN);
    private static final Position CHO_BOTTOM_RIGHT = new Position(Column.F, Row.NINE);
    private static final Position CHO_CENTER = new Position(Column.E, Row.EIGHT);

    public static boolean isInside(Position position) {
        return isInsideHanPalace(position) || isInsideChoPalace(position);
    }

    private static boolean isInsideHanPalace(Position position) {
        return position.isBetween(HAN_TOP_LEFT, HAN_BOTTOM_RIGHT);
    }

    private static boolean isInsideChoPalace(Position position) {
        return position.isBetween(CHO_TOP_LEFT, CHO_BOTTOM_RIGHT);
    }
}
