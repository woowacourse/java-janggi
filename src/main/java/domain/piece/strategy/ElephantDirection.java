package domain.piece.strategy;

import domain.board.Position;

import java.util.Arrays;
import java.util.List;

public enum ElephantDirection {
    UP_RIGHT(new Delta(2, 3), new BlockedPath(List.of(new Delta(0, 1), new Delta(1, 2)))),
    UP_LEFT(new Delta(-2, 3), new BlockedPath(List.of(new Delta(0, 1), new Delta(-1, 2)))),
    DOWN_RIGHT(new Delta(2, -3), new BlockedPath(List.of(new Delta(0, -1), new Delta(1, -2)))),
    DOWN_LEFT(new Delta(-2, -3), new BlockedPath(List.of(new Delta(0, -1), new Delta(-1, -2)))),
    LEFT_UP(new Delta(-3, 2), new BlockedPath(List.of(new Delta(-1, 0), new Delta(-2, 1)))),
    LEFT_DOWN(new Delta(-3, -2), new BlockedPath(List.of(new Delta(-1, 0), new Delta(-2, -1)))),
    RIGHT_UP(new Delta(3, 2), new BlockedPath(List.of(new Delta(1, 0), new Delta(2, 1)))),
    RIGHT_DOWN(new Delta(3, -2), new BlockedPath(List.of(new Delta(1, 0), new Delta(2, -1))));

    private static final String INVALID_ELEPHANT_MOVE_ERROR_MESSAGE = "[ERROR] 상의 이동 방향이 올바르지 않습니다.";

    private final Delta delta;
    private final BlockedPath blockedPath;

    ElephantDirection(Delta delta, BlockedPath blockedPath) {
        this.delta = delta;
        this.blockedPath = blockedPath;
    }

    public static ElephantDirection from(Delta delta) {
        return Arrays.stream(values())
                .filter(direction -> direction.matches(delta))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_ELEPHANT_MOVE_ERROR_MESSAGE));
    }

    private boolean matches(Delta delta) {
        return this.delta.dx() == delta.dx() && this.delta.dy() == delta.dy();
    }

    public List<Position> findPath(Position from) {
        return blockedPath.findPath(from);
    }
}
