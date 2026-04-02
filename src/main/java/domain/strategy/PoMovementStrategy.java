package domain.strategy;

import domain.board.PathPieces;

public class PoMovementStrategy implements MovementStrategy {
    @Override
    public boolean isValidPath(PathPieces pathPieces) {
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
