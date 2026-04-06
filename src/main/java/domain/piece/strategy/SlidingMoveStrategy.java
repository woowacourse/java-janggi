package domain.piece.strategy;

import domain.piece.strategy.component.PalaceMoveRule;
import domain.position.Position;
import java.util.List;

public class SlidingMoveStrategy implements MoveStrategy {
    private final PalaceMoveRule palaceMoveRule;

    public SlidingMoveStrategy(PalaceMoveRule palaceMoveRule) {
        this.palaceMoveRule = palaceMoveRule;
    }

    @Override
    public List<Position> findMovablePath(Position start, Position destination) {
        if (start.isSameRow(destination.getRow())) {
            return start.getSameRowPositionsToDestination(destination);
        }
        if (start.isSameColumn(destination.getColumn())) {
            return start.getSameColumnPositionsToDestination(destination);
        }
        if (palaceMoveRule.isPalacePath(start, destination)) {
            return List.of();
        }
        throw new IllegalArgumentException(MoveStrategyErrorMessage.NOT_EXIST_MOVABLE_PATH.getMessage());
    }
}
