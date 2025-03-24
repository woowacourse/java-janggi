package janggi.domain.piece.movement.fixed;

import janggi.domain.piece.Pieces;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.movement.MovementStrategy;

public interface FixedMovementStrategy extends MovementStrategy {

    @Override
    default boolean isMoveable(Pieces map, Position origin, Side side, Position destination) {
        return isLegalDestination(origin, destination) && isPathClear(map, side, origin, destination);
    }

    default boolean isPathClear(Pieces map, Side side, Position origin, Position destination) {
        Pieces onPathPieces = getAllPiecesOnPath(map, origin, destination);

        if (!onPathPieces.isEmpty()) {
            return onPathPieces.isEnemyOnDestination(side, destination);
        }
        return true;
    }

    boolean isLegalDestination(Position origin, Position destination);

    Pieces getAllPiecesOnPath(Pieces map, Position origin, Position destination);
}
