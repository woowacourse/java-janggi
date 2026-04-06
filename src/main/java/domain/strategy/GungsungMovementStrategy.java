package domain.strategy;

import domain.board.PathPieces;
import domain.position.Path;

public class GungsungMovementStrategy implements MovementStrategy {
    @Override
    public boolean isValidPath(Path path, PathPieces pathPieces) {
        if (!path.destination().isGungsung()) {
            return false;
        }
        return pathPieces.isDestinationEmpty() || pathPieces.isDestinationDifferentTeamFromSource();
    }
}
