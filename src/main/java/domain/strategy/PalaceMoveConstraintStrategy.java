package domain.strategy;

import domain.board.PathPieces;

public class PalaceMoveConstraintStrategy implements MovementStrategy {

    private final MovementStrategy delegate;

    public PalaceMoveConstraintStrategy(MovementStrategy delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean validatePath(PathPieces pathPieces) {
        return validatePalaceMove(pathPieces) && delegate.validatePath(pathPieces);
    }

    private boolean validatePalaceMove(PathPieces pathPieces) {
        return pathPieces.isOrthogonalMove() || (pathPieces.isPalaceMove() && pathPieces.isValidPalaceDiagonalMove());
    }
}


