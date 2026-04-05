package janggi.domain.piece.direction;

import janggi.domain.point.Point;
import java.util.List;
import java.util.Map;

public enum CastleDirection {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP_RIGHT(1, 1),
    UP_LEFT(-1, 1),
    DOWN_RIGHT(1, -1),
    DOWN_LEFT(-1, -1),
    ;

    private static final Map<Point, List<CastleDirection>> RULE = Map.ofEntries(
            Map.entry(Point.of(3, 0), List.of(RIGHT, UP, UP_RIGHT)),
            Map.entry(Point.of(4, 0), List.of(LEFT, RIGHT, UP)),
            Map.entry(Point.of(5, 0), List.of(LEFT, UP, UP_LEFT)),
            Map.entry(Point.of(3, 1), List.of(DOWN, RIGHT, UP)),
            Map.entry(Point.of(4, 1), List.of(DOWN, DOWN_LEFT, DOWN_RIGHT, LEFT, RIGHT, UP, UP_LEFT, UP_RIGHT)),
            Map.entry(Point.of(5, 1), List.of(DOWN, LEFT, UP)),
            Map.entry(Point.of(3, 2), List.of(DOWN, DOWN_RIGHT, RIGHT)),
            Map.entry(Point.of(4, 2), List.of(DOWN, LEFT, RIGHT)),
            Map.entry(Point.of(5, 2), List.of(DOWN, DOWN_LEFT, LEFT)),
            Map.entry(Point.of(3, 7), List.of(RIGHT, UP, UP_RIGHT)),
            Map.entry(Point.of(4, 7), List.of(LEFT, RIGHT, UP)),
            Map.entry(Point.of(5, 7), List.of(LEFT, UP, UP_LEFT)),
            Map.entry(Point.of(3, 8), List.of(UP, DOWN, RIGHT)),
            Map.entry(Point.of(4, 8), List.of(DOWN, DOWN_LEFT, DOWN_RIGHT, LEFT, RIGHT, UP, UP_LEFT, UP_RIGHT)),
            Map.entry(Point.of(5, 8), List.of(UP, DOWN, LEFT)),
            Map.entry(Point.of(3, 9), List.of(DOWN, DOWN_RIGHT, RIGHT)),
            Map.entry(Point.of(4, 9), List.of(LEFT, RIGHT, DOWN)),
            Map.entry(Point.of(5, 9), List.of(DOWN, DOWN_LEFT, LEFT))
    );

    private final int targetCol;
    private final int targetRow;

    CastleDirection(int targetCol, int targetRow) {
        this.targetCol = targetCol;
        this.targetRow = targetRow;
    }

    public static CastleDirection find(Point point, int directionCol, int directionRow) {
        return RULE.get(point).stream()
                .filter(dir -> dir.targetCol == directionCol && dir.targetRow == directionRow)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 이동할 수 없는 방향입니다."));
    }

    public int getTargetCol() {
        return targetCol;
    }

    public int getTargetRow() {
        return targetRow;
    }
}
