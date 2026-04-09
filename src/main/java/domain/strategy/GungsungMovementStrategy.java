package domain.strategy;

import domain.board.PathPieces;
import domain.gungsung.Gungsung;
import domain.position.Path;

public class GungsungMovementStrategy implements MovementStrategy {
    private static final Gungsung GUNGSUNG = new Gungsung();

    @Override
    public boolean isValidPath(Path path, PathPieces pathPieces) {
        if (!GUNGSUNG.isGungsung(path.destination())) {
            return false;
        }
        return pathPieces.isDestinationEmpty() || pathPieces.isDestinationDifferentTeamFromSource();
    }
}
