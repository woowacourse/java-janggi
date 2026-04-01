package domain.piece.strategy;

import domain.piece.strategy.component.PalaceMoveRule;
import domain.position.Position;
import java.util.List;
import java.util.stream.IntStream;

public class JolMoveStrategy implements MoveStrategy {
    private static final int[] D_ROW = {1, 0, 0};
    private static final int[] D_COLUMN = {0, -1, 1};

    private final PalaceMoveRule palaceMoveRule;

    public JolMoveStrategy(PalaceMoveRule palaceMoveRule) {
        this.palaceMoveRule = palaceMoveRule;
    }

    @Override
    public List<Position> findMovablePath(Position start, Position destination) {
        if (palaceMoveRule.isPalacePath(start, destination)) {
            return List.of();
        }
        return IntStream.range(0, D_ROW.length)
                .filter(index -> isEqualToDestination(index, start, destination))
                .mapToObj(index -> List.<Position>of())
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException(MoveStrategyErrorMessage.NOT_EXIST_MOVABLE_PATH.getMessage())
                );
    }

    private boolean isEqualToDestination(int index, Position start, Position destination) {
        Position changedPosition = start.go(D_ROW[index], D_COLUMN[index]);
        return changedPosition.equals(destination);
    }
}
