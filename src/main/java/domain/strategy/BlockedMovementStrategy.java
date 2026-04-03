package domain.strategy;

import domain.board.PathPieces;

public class BlockedMovementStrategy implements MovementStrategy {
    @Override
    public boolean isValidPath(PathPieces pathPieces) {
        if (pathPieces.hasPieceInWaypoint()) {
            return false;
        }
        return pathPieces.isDestinationEmpty() || pathPieces.isDestinationDifferentTeamFromSource();
    }
}
