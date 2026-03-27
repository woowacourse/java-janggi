package domain.strategy;

import domain.board.PathPieces;
import domain.piece.Piece;
import java.util.List;

public class PoMovementStrategy implements MovementStrategy {
    @Override
    public boolean validatePath(PathPieces pathPieces) {
        List<Piece> waypointPieces = pathPieces.getWaypointPieces();
        if (waypointPieces.size() != 1) {
            return false;
        }
        if (waypointPieces.getFirst().isPo() || pathPieces.getDestinationPiece().isPo()) {
            return false;
        }

        return pathPieces.getSourcePiece().isDifferentTeam(pathPieces.getDestinationPiece());
    }
}
