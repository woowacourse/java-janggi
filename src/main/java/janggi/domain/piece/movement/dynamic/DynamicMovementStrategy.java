package janggi.domain.piece.movement.dynamic;

import janggi.domain.Side;
import janggi.domain.piece.Pieces;
import janggi.domain.piece.Position;
import janggi.domain.piece.movement.MovementStrategy;

public interface DynamicMovementStrategy extends MovementStrategy {

    @Override
    default boolean isMoveable(Pieces map, Position origin, Side side, Position destination) {
        return isLegalDestination(side, origin, destination) && isLegalPath(map, side, origin, destination);
    }

    boolean isLegalDestination(Side side, Position origin, Position destination);

    boolean isLegalPath(Pieces existingPieces, Side side, Position origin, Position destination);
}
