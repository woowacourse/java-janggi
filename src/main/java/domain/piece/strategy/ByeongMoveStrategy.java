package domain.piece.strategy;

import domain.position.Position;
import java.util.List;
import java.util.stream.IntStream;

public class ByeongMoveStrategy implements MoveStrategy {
    private static final int[] D_ROW = {-1, 0, 0};
    private static final int[] D_COLUMN = {0, -1, 1};

    @Override
    public List<Position> findMovablePath(Position start, Position destination) {
        return IntStream.range(0, D_ROW.length)
                .filter(index -> isEqualToDestination(start, destination, index))
                .mapToObj(index -> List.<Position>of())
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException(MoveStrategyErrorMessage.NOT_EXIST_MOVABLE_PATH.getMessage())
                );
    }

    private boolean isEqualToDestination(Position start, Position destination, int i) {
        Position changedPosition = start.go(D_ROW[i], D_COLUMN[i]);
        return changedPosition.equals(destination);
    }
}
