package domain.piece.strategy;

import domain.board.Position;

import java.util.Arrays;
import java.util.List;

public enum HorseDirection {
    UP_RIGHT(1, 2, 0, 1),
    UP_LEFT(-1, 2, 0, 1),
    DOWN_RIGHT(1, -2, 0, -1),
    DOWN_LEFT(-1, -2, 0, -1),
    LEFT_UP(-2, 1, -1, 0),
    LEFT_DOWN(-2, -1, -1, 0),
    RIGHT_UP(2, 1, 1, 0),
    RIGHT_DOWN(2, -1, 1, 0);

    private final int dx;
    private final int dy;
    private final int pathX;
    private final int pathY;

    HorseDirection(int dx, int dy, int pathX, int pathY) {
        this.dx = dx;
        this.dy = dy;
        this.pathX = pathX;
        this.pathY = pathY;
    }

    public static HorseDirection from(int dx, int dy) {
        return Arrays.stream(values())
                .filter(direction -> direction.matches(dx, dy))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 마의 이동 방향이 올바르지 않습니다."));
    }

    private boolean matches(int dx, int dy) {
        return this.dx == dx && this.dy == dy;
    }

    public List<Position> findPath(Position from) {
        return List.of(new Position(from.x() + pathX, from.y() + pathY));
    }
}
