package piece;

import java.util.List;
import java.util.function.Supplier;
import position.Position;

public enum MoveDirection {

    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP_LEFT(-1, 1),
    UP_RIGHT(1, 1),
    DOWN_LEFT(-1, -1),
    DOWN_RIGHT(1, -1),
    CROSS_INF(0, 0),
    ;

    private final int x;
    private final int y;

    MoveDirection(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public List<Position> calculateNextPositionsFrom(Position currentPosition) {
        if (this == CROSS_INF) {
            return currentPosition.getAllCrossPositions();
        }
        return getPositionIf(
                () -> currentPosition.isValidToAdd(x, y),
                () -> currentPosition.add(x, y)
        );

    }

    private List<Position> getPositionIf(
            Supplier<Boolean> decision,
            Supplier<Position> result
    ) {
        if (!decision.get()) {
            return List.of();
        }
        return List.of(result.get());
    }
}
