package domain.strategy;

import domain.board.PathPieces;

public class PalaceMovementStrategy implements MovementStrategy{

    @Override
    public boolean validatePath(PathPieces pathPieces) {
        if(pathPieces.isSourceInPalace() && pathPieces.isDestinationInPalace()) {
            return pathPieces.isMovableDestination();
        }
        return false;
    }
}
