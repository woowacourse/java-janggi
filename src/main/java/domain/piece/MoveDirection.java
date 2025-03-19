package domain.piece;

import domain.position.Position;

import java.util.List;
import java.util.function.Supplier;

public enum MoveDirection {

    CROSS_INF,
    UP,
    DOWN,
    LEFT,
    RIGHT,

    UP_LEFT,
    UP_RIGHT,
    DOWN_LEFT,
    DOWN_RIGHT,
    ;

    public List<Position> calculateNextPositionsFrom(Position currentPosition) {
        return switch (this) {
            case UP -> getPositionIf(
                    () -> currentPosition.isValidToAdd(0, 1),
                    () -> currentPosition.add(0, 1)
            );
            case DOWN -> getPositionIf(
                    () -> currentPosition.isValidToAdd(0, -1),
                    () -> currentPosition.add(0, -1)
            );
            case RIGHT -> getPositionIf(
                    () -> currentPosition.isValidToAdd(1, 0),
                    () -> currentPosition.add(1, 0)
            );
            case LEFT -> getPositionIf(
                    () -> currentPosition.isValidToAdd(-1, 0),
                    () -> currentPosition.add(-1,0 )
            );
            case UP_RIGHT -> getPositionIf(
                    () -> currentPosition.isValidToAdd(1, 1),
                    () -> currentPosition.add(1, 1)
            );
            case UP_LEFT -> getPositionIf(
                    () -> currentPosition.isValidToAdd(-1, 1),
                    () -> currentPosition.add(-1, 1)
            );
            case DOWN_RIGHT -> getPositionIf(
                    () -> currentPosition.isValidToAdd(1, -1),
                    () -> currentPosition.add(1, -1)
            );
            case DOWN_LEFT -> getPositionIf(
                    () -> currentPosition.isValidToAdd(-1, -1),
                    () -> currentPosition.add(-1, -1)
            );
            case CROSS_INF -> currentPosition.getAllCrossPositions();
        };
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
