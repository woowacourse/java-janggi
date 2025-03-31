package janggi.domain.piece.movement;

import janggi.domain.Palace;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.movement.palace.PalaceMovementStrategy;
import janggi.domain.piece.pieces.PiecesView;

public class MovementStrategyContext implements MovementStrategy {

    private final MovementStrategySelector strategySelector;

    public MovementStrategyContext(
        MovementStrategy defaultStrategy,
        PalaceMovementStrategy palaceStrategy
    ) {
        this.strategySelector = new MovementStrategySelector(defaultStrategy, palaceStrategy);
    }

    @Override
    public boolean isMoveable(PiecesView map, Position origin, Side side, Position destination) {
        return strategySelector.selectStrategy(origin).isMoveable(map, origin, side, destination);
    }

    private record MovementStrategySelector(
        MovementStrategy defaultMovementStrategy,
        PalaceMovementStrategy palaceMovementStrategy
    ) {

        MovementStrategy selectStrategy(Position position) {
            return Palace.isInPalace(position) ? palaceMovementStrategy : defaultMovementStrategy;
        }
    }
}
