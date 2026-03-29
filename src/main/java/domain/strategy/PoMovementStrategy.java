package domain.strategy;

import domain.board.PathPieces;
import domain.piece.PieceType;

public class PoMovementStrategy implements MovementStrategy {

    @Override
    public boolean validatePath(PathPieces pathPieces) {
        if (pathPieces.countWaypoints() != 1) {
            return false;
        }

        if (pathPieces.hasPieceOnPath(PieceType.PO) || pathPieces.isDestinationType(PieceType.PO)) {
            return false;
        }

        return pathPieces.isMovableDestination();
    }
}