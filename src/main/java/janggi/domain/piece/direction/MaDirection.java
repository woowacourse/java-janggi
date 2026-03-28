package janggi.domain.piece.direction;

import java.util.Arrays;

public enum MaDirection {
    UP_LEFT(-1, -2, 0, -1),
    UP_RIGHT(1, -2, 0, -1),
    DOWN_LEFT(-1, 2, 0, 1),
    DOWN_RIGHT(1, 2, 0, 1),
    LEFT_UP(-2, -1, -1, 0),
    LEFT_DOWN(-2, 1, -1, 0),
    RIGHT_UP(2, -1, 1, 0),
    RIGHT_DOWN(2, 1, 1, 0);

    private final int targetCol;
    private final int targetRow;
    private final int routeCol;
    private final int routeRow;

    MaDirection(int targetCol, int targetRow, int routeCol, int routeRow) {
        this.targetCol = targetCol;
        this.targetRow = targetRow;
        this.routeCol = routeCol;
        this.routeRow = routeRow;
    }

    public static MaDirection find(int directionCol, int directionRow) {
        return Arrays.stream(values())
                .filter(dir -> dir.targetCol == directionCol && dir.targetRow == directionRow)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 마가 이동할 수 없는 방향입니다."));
    }

    public int getRouteCol() {
        return routeCol;
    }

    public int getRouteRow() {
        return routeRow;
    }
}
