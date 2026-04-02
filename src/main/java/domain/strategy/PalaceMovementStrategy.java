package domain.strategy;

import domain.board.PathPieces;

public class PalaceMovementStrategy implements MovementStrategy{

    @Override
    public boolean validatePath(PathPieces pathPieces) {
        if(pathPieces.isSourceInPalace() && pathPieces.isDestinationInPalace() && pathPieces.isValidPalaceDiagonalMove()) {
            return pathPieces.isMovableDestination();
        }
        return false;
    }
}
