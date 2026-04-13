package domain.movement.palace;

import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import java.util.List;
import java.util.Map;

public class Palace {
    private static final Position HAN_TOP_LEFT = new Position(Column.D, Row.ZERO);
    private static final Position HAN_TOP_RIGHT = new Position(Column.F, Row.ZERO);
    private static final Position HAN_BOTTOM_LEFT = new Position(Column.D, Row.TWO);
    private static final Position HAN_BOTTOM_RIGHT = new Position(Column.F, Row.TWO);
    private static final Position HAN_CENTER = new Position(Column.E, Row.ONE);

    private static final Position CHO_TOP_LEFT = new Position(Column.D, Row.SEVEN);
    private static final Position CHO_TOP_RIGHT = new Position(Column.F, Row.SEVEN);
    private static final Position CHO_BOTTOM_LEFT = new Position(Column.D, Row.NINE);
    private static final Position CHO_BOTTOM_RIGHT = new Position(Column.F, Row.NINE);
    private static final Position CHO_CENTER = new Position(Column.E, Row.EIGHT);
    private static final List<Position> HAN_CORNERS = List.of(
            HAN_TOP_LEFT,
            HAN_TOP_RIGHT,
            HAN_BOTTOM_LEFT,
            HAN_BOTTOM_RIGHT
    );
    private static final List<Position> CHO_CORNERS = List.of(
            CHO_TOP_LEFT,
            CHO_TOP_RIGHT,
            CHO_BOTTOM_LEFT,
            CHO_BOTTOM_RIGHT
    );
    private static final Map<Position, Position> OPPOSITE_CORNERS = Map.ofEntries(
            Map.entry(HAN_TOP_LEFT, HAN_BOTTOM_RIGHT),
            Map.entry(HAN_BOTTOM_RIGHT, HAN_TOP_LEFT),
            Map.entry(HAN_TOP_RIGHT, HAN_BOTTOM_LEFT),
            Map.entry(HAN_BOTTOM_LEFT, HAN_TOP_RIGHT),
            Map.entry(CHO_TOP_LEFT, CHO_BOTTOM_RIGHT),
            Map.entry(CHO_BOTTOM_RIGHT, CHO_TOP_LEFT),
            Map.entry(CHO_TOP_RIGHT, CHO_BOTTOM_LEFT),
            Map.entry(CHO_BOTTOM_LEFT, CHO_TOP_RIGHT)
    );

    public static boolean isInside(Position position) {
        return isInsideHanPalace(position) || isInsideChoPalace(position);
    }

    public static boolean isCenter(Position position) {
        return position.equals(HAN_CENTER) || position.equals(CHO_CENTER);
    }

    public static boolean isCorner(Position position) {
        return HAN_CORNERS.contains(position) || CHO_CORNERS.contains(position);
    }

    public static Position centerOf(Position position) {
        if (isInsideHanPalace(position)) {
            return HAN_CENTER;
        }
        if (isInsideChoPalace(position)) {
            return CHO_CENTER;
        }
        throw new IllegalArgumentException("[ERROR] 궁성 내부 좌표가 아닙니다: " + position);
    }

    public static List<Position> cornersOf(Position position) {
        if (isInsideHanPalace(position)) {
            return HAN_CORNERS;
        }
        if (isInsideChoPalace(position)) {
            return CHO_CORNERS;
        }
        return List.of();
    }

    public static Position oppositeCornerOf(Position position) {
        return OPPOSITE_CORNERS.get(position);
    }

    private static boolean isInsideHanPalace(Position position) {
        return position.isBetween(HAN_TOP_LEFT, HAN_BOTTOM_RIGHT);
    }

    private static boolean isInsideChoPalace(Position position) {
        return position.isBetween(CHO_TOP_LEFT, CHO_BOTTOM_RIGHT);
    }
}
