package domain.strategy;

import domain.board.PathPieces;
import domain.piece.Piece;
import java.util.List;

public class BlockedMovementStrategy implements MovementStrategy {
    @Override
    public boolean validatePath(PathPieces pathPieces) {
        List<Piece> waypointPieces = pathPieces.getWaypointPieces();
        if (!waypointPieces.isEmpty()) {
            return false;
        }
        if (pathPieces.getSrcPiece().isSameTeam(pathPieces.getDestPiece())) {
            return false;
        }
        return true;
    }
}
