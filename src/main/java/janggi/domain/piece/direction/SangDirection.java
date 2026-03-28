package janggi.domain.piece.direction;

import java.util.Arrays;

public enum SangDirection {
    UP_LEFT(-2, -3, 0, -1, -1, -2),
    UP_RIGHT(2, -3, 0, -1, 1, -2),
    DOWN_LEFT(-2, 3, 0, 1, -1, 2),
    DOWN_RIGHT(2, 3, 0, 1, 1, 2),

    LEFT_UP(-3, -2, -1, 0, -2, -1),
    LEFT_DOWN(-3, 2, -1, 0, -2, 1),
    RIGHT_UP(3, -2, 1, 0, 2, -1),
    RIGHT_DOWN(3, 2, 1, 0, 2, 1);

    private final int targetCol;
    private final int targetRow;
    private final int route1Col;
    private final int route1Row;
    private final int route2Col;
    private final int route2Row;

    SangDirection(int targetCol, int targetRow, int route1Col, int route1Row, int route2Col, int route2Row) {
        this.targetCol = targetCol;
        this.targetRow = targetRow;
        this.route1Col = route1Col;
        this.route1Row = route1Row;
        this.route2Col = route2Col;
        this.route2Row = route2Row;
    }

    public static SangDirection find(int directionCol, int directionRow) {
        return Arrays.stream(values())
                .filter(dir -> dir.targetCol == directionCol && dir.targetRow == directionRow)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 상이 이동할 수 없는 방향입니다."));
    }

    public int getRoute1Col() {
        return route1Col;
    }

    public int getRoute1Row() {
        return route1Row;
    }

    public int getRoute2Col() {
        return route2Col;
    }

    public int getRoute2Row() {
        return route2Row;
    }
}
