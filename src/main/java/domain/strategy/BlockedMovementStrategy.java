package domain.strategy;

import domain.board.PathPieces;

public class BlockedMovementStrategy implements MovementStrategy {

    @Override
    public boolean validatePath(PathPieces pathPieces) {
        if (pathPieces.countWaypoints() != 0) {
            return false;
        }

        return pathPieces.isMovableDestination();
    }
}