package domain.board;

import domain.piece.Piece;
import java.util.List;

public class PathPieces {
    Piece srcPiece;
    List<Piece> waypointPieces;
    Piece destPiece;

    public PathPieces(Piece srcPiece, List<Piece> waypointPieces, Piece destPiece) {
        this.srcPiece = srcPiece;
        this.waypointPieces = waypointPieces;
        this.destPiece = destPiece;
    }

    public Piece getSrcPiece() {
        return srcPiece;
    }

    public List<Piece> getWaypointPieces() {
        return waypointPieces;
    }

    public Piece getDestPiece() {
        return destPiece;
    }
}
