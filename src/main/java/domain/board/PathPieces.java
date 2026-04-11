package domain.board;

import domain.piece.BasicPiece;
import domain.piece.None;
import domain.piece.PieceType;

import java.util.List;

public class PathPieces {
    private final BasicPiece sourcePiece;
    private final List<BasicPiece> waypointPieces;
    private final BasicPiece destinationPiece;

    public PathPieces(BasicPiece sourcePiece, List<BasicPiece> waypointPieces) {
        this(sourcePiece, waypointPieces, None.getInstance());
    }

    public PathPieces(BasicPiece sourcePiece, List<BasicPiece> waypointPieces, BasicPiece destinationPiece) {
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
        return destinationPiece.isType(type);
    }

    public boolean isMovableDestination() {
        return sourcePiece.isDifferentTeam(destinationPiece);
    }
}
