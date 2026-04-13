package domain.strategy;

import domain.board.PathPieces;
import domain.position.Path;

public class BlockedMovementStrategy implements MovementStrategy {
    @Override
    public boolean isValidPath(Path path, PathPieces pathPieces) {
        if (pathPieces.hasPieceInWaypoint()) {
            return false;
        }
        return pathPieces.isDestinationEmpty() || pathPieces.isDestinationDifferentTeamFromSource();
    }
}
