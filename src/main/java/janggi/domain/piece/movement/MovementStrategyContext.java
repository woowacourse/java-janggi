package janggi.domain.piece.movement;

import janggi.domain.Palace;
import janggi.domain.piece.Position;
import janggi.domain.piece.movement.palace.PalaceMovementStrategy;

public record MovementStrategyContext(
    MovementStrategy defaultMovementStrategy,
    PalaceMovementStrategy palaceMovementStrategy
) {

    public MovementStrategy getMovementStrategy(Position position) {
        return Palace.isInPalace(position) ? palaceMovementStrategy : defaultMovementStrategy;
    }
}
