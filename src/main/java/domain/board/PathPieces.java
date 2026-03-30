package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.List;

public class PathPieces {
    private final Piece sourcePiece;
    private final List<Piece> waypointPieces;
    private final Piece destinationPiece;

    public PathPieces(Piece sourcePiece, List<Piece> waypointPieces, Piece destinationPiece) {
        this.sourcePiece = sourcePiece;
        this.waypointPieces = waypointPieces;
        this.destinationPiece = destinationPiece;
    }

    public long countWaypoints() {
        return waypointPieces.stream()
                .filter(Piece::isNotNone)
                .count();
    }

    public boolean hasPieceOnPath(PieceType type) {
        return waypointPieces.stream()
                .anyMatch(p -> p.getPieceType() == type);
    }

    public boolean isDestinationType(PieceType type) {
        return destinationPiece.getPieceType() == type;
    }

    public boolean isMovableDestination() {
        return sourcePiece.isDifferentTeam(destinationPiece);
    }
}
