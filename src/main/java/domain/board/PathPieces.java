package domain.board;

import domain.piece.Piece;
import java.util.List;

public final class PathPieces {
    private final Piece sourcePiece;
    private final List<Piece> waypointPieces;
    private final Piece destinationPiece;

    public PathPieces(Piece sourcePiece, List<Piece> waypointPieces, Piece destinationPiece) {
        this.sourcePiece = sourcePiece;
        this.waypointPieces = List.copyOf(waypointPieces);
        this.destinationPiece = destinationPiece;
    }

    public boolean isDestinationEmpty() {
        return destinationPiece.isNone();
    }

    public boolean isDestinationDifferentTeamFromSource() {
        return (!destinationPiece.isNone()) && sourcePiece.isDifferentTeam(destinationPiece);
    }

    public boolean hasPieceInWaypoint() {
        return !waypointPieces.isEmpty();
    }

    public boolean hasOnePieceInWaypoint() {
        return waypointPieces.size() == 1;
    }

    public boolean hasPoInWaypoint() {
        return waypointPieces.stream()
                .anyMatch(Piece::isPo);
    }

    public boolean isDestinationPiecePo() {
        return destinationPiece.isPo();
    }
}
