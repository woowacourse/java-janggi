package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.List;

public class PathPieces {
    private final Piece sourcePiece;
    private final List<Piece> waypointPieces;
    private final Piece destinationPiece;

    public PathPieces(Piece sourcePiece, List<Piece> waypointPieces) {
        this.sourcePiece = sourcePiece;
        this.waypointPieces = waypointPieces;
        this.destinationPiece = null;
    }

    public PathPieces(Piece sourcePiece, List<Piece> waypointPieces, Piece destinationPiece) {
        this.sourcePiece = sourcePiece;
        this.waypointPieces = waypointPieces;
        this.destinationPiece = destinationPiece;
    }

    public long countWaypoints() {
        return waypointPieces.size();
    }

    public boolean hasPieceOnPath(PieceType type) {
        return waypointPieces.stream()
                .anyMatch(p -> p.isType(type));
    }

    public boolean isDestinationType(PieceType type) {
        if (destinationPiece == null) {
            return false;
        }
        return destinationPiece.isType(type);
    }

    public boolean isMovableDestination() {
        if (destinationPiece == null) {
            return true;
        }
        return sourcePiece.isDifferentTeam(destinationPiece);
    }
}
