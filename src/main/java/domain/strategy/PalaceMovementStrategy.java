package domain.strategy;

import domain.board.PathPieces;

public class PalaceMovementStrategy implements MovementStrategy {

    @Override
    public boolean validatePath(PathPieces pathPieces) {
        if (!pathPieces.isSourceInPalace() || !pathPieces.isDestinationInPalace()) {
            return false;
        }

        if (!pathPieces.isOrthogonalMove() && !pathPieces.isValidPalaceDiagonalMove()) {
            return false;
        }

        return pathPieces.isMovableDestination();
    }
}
