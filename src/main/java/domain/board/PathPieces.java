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

    public Piece getSourcePiece() {
        return sourcePiece;
    }

    public List<Piece> getWaypointPieces() {
        return waypointPieces;
    }

    public Piece getDestinationPiece() {
        return destinationPiece;
    }
}
