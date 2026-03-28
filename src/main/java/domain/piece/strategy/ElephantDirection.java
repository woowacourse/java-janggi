package domain.piece.strategy;

import domain.board.Position;

import java.util.Arrays;
import java.util.List;

public enum ElephantDirection {
    UP_RIGHT(2, 3, 0, 1, 1, 2),
    UP_LEFT(-2, 3, 0, 1, -1, 2),
    DOWN_RIGHT(2, -3, 0, -1, 1, -2),
    DOWN_LEFT(-2, -3, 0, -1, -1, -2),
    LEFT_UP(-3, 2, -1, 0, -2, 1),
    LEFT_DOWN(-3, -2, -1, 0, -2, -1),
    RIGHT_UP(3, 2, 1, 0, 2, 1),
    RIGHT_DOWN(3, -2, 1, 0, 2, -1);

    private static final String INVALID_ELEPHANT_MOVE_ERROR_MESSAGE = "[ERROR] 상의 이동 방향이 올바르지 않습니다.";

    private final int dx;
    private final int dy;
    private final int firstPathX;
    private final int firstPathY;
    private final int secondPathX;
    private final int secondPathY;

    ElephantDirection(int dx, int dy, int firstPathX, int firstPathY, int secondPathX, int secondPathY) {
        this.dx = dx;
        this.dy = dy;
        this.firstPathX = firstPathX;
        this.firstPathY = firstPathY;
        this.secondPathX = secondPathX;
        this.secondPathY = secondPathY;
    }

    public static ElephantDirection from(int dx, int dy) {
        return Arrays.stream(values())
                .filter(direction -> direction.matches(dx, dy))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_ELEPHANT_MOVE_ERROR_MESSAGE));
    }

    private boolean matches(int dx, int dy) {
        return this.dx == dx && this.dy == dy;
    }

    public List<Position> findPath(Position from) {
        return List.of(
                new Position(from.x() + firstPathX, from.y() + firstPathY),
                new Position(from.x() + secondPathX, from.y() + secondPathY)
        );
    }
}
