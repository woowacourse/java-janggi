package domain.board;

import domain.piece.Piece;
import java.util.List;

public class PathPieces {
    Piece sourcePiece;
    List<Piece> waypointPieces;
    Piece destinationPiece;

    public PathPieces(Piece sourcePiece, List<Piece> waypointPieces, Piece destinationPiece) {
        this.sourcePiece = sourcePiece;
        this.waypointPieces = waypointPieces;
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
