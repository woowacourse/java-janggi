package domain.piece.strategy;

import domain.board.Position;

import java.util.Arrays;
import java.util.List;

public enum HorseDirection {
    UP_RIGHT(new Delta(1, 2), new BlockedPath(List.of(new Delta(0, 1)))),
    UP_LEFT(new Delta(-1, 2), new BlockedPath(List.of(new Delta(0, 1)))),
    DOWN_RIGHT(new Delta(1, -2), new BlockedPath(List.of(new Delta(0, -1)))),
    DOWN_LEFT(new Delta(-1, -2), new BlockedPath(List.of(new Delta(0, -1)))),
    LEFT_UP(new Delta(-2, 1), new BlockedPath(List.of(new Delta(-1, 0)))),
    LEFT_DOWN(new Delta(-2, -1), new BlockedPath(List.of(new Delta(-1, 0)))),
    RIGHT_UP(new Delta(2, 1), new BlockedPath(List.of(new Delta(1, 0)))),
    RIGHT_DOWN(new Delta(2, -1), new BlockedPath(List.of(new Delta(1, 0))));

    private static final String INVALID_HORSE_MOVE_ERROR_MESSAGE = "[ERROR] 마의 이동 방향이 올바르지 않습니다.";

    private final Delta delta;
    private final BlockedPath blockedPath;

    HorseDirection(Delta delta, BlockedPath blockedPath) {
        this.delta = delta;
        this.blockedPath = blockedPath;
    }

    public static HorseDirection from(Delta delta) {
        return Arrays.stream(values())
                .filter(direction -> direction.matches(delta))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_HORSE_MOVE_ERROR_MESSAGE));
    }

    private boolean matches(Delta delta) {
        return this.delta.dx() == delta.dx() && this.delta.dy() == delta.dy();
    }

    public List<Position> findPath(Position from) {
        return blockedPath.findPath(from);
    }
}
