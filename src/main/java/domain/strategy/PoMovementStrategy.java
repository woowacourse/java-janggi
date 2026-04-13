package domain.strategy;

import domain.board.PathPieces;
import domain.position.Path;

public class PoMovementStrategy implements MovementStrategy {
    @Override
    public boolean isValidPath(Path path, PathPieces pathPieces) {
        if (!pathPieces.hasOnePieceInWaypoint()) {
            return false;
        }
        if (pathPieces.hasPoInWaypoint()) {
            return false;
        }
        if (pathPieces.isDestinationPiecePo()) {
            return false;
        }
        return pathPieces.isDestinationEmpty() || pathPieces.isDestinationDifferentTeamFromSource();
    }
}
