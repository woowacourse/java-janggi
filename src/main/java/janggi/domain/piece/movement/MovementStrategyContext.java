package janggi.domain.piece.movement;

import janggi.domain.Palace;
import janggi.domain.piece.Position;
import janggi.domain.piece.movement.palace.PalaceMovementStrategy;

public class MovementStrategyContext {

    private final MovementStrategy defaultMovementStrategy;
    private final PalaceMovementStrategy palaceMovementStrategy;

    public MovementStrategyContext(
        MovementStrategy defaultMovementStrategy,
        PalaceMovementStrategy palaceMovementStrategy
    ) {
        this.defaultMovementStrategy = defaultMovementStrategy;
        this.palaceMovementStrategy = palaceMovementStrategy;
    }

    public MovementStrategy getMovementStrategy(Position position) {
        return Palace.isInPalace(position) ? palaceMovementStrategy : defaultMovementStrategy;
    }
}
