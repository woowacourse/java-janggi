package domain.strategy;

import domain.board.PathPieces;
import domain.piece.PieceType;

public class PoMovementStrategy implements MovementStrategy {

    private static final int PO_COUNT_WAYPOINTS = 1;

    @Override
    public boolean validatePath(PathPieces pathPieces) {
        if (pathPieces.countWaypoints() != PO_COUNT_WAYPOINTS) {
            return false;
        }

        if (pathPieces.hasPieceOnPath(PieceType.PO) || pathPieces.isDestinationType(PieceType.PO)) {
            return false;
        }

        return pathPieces.isMovableDestination();
    }
}