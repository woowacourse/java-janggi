package domain.board;

import domain.piece.BasicPiece;
import domain.piece.None;
import domain.piece.PieceType;

import java.util.List;

public class PathPieces {
    private final BasicPiece sourcePiece;
    private final List<BasicPiece> waypointPieces;
    private final BasicPiece destinationPiece;
    private final MoveMeta moveMeta;

    public PathPieces(BasicPiece sourcePiece, List<BasicPiece> waypointPieces) {
        this(sourcePiece, waypointPieces, None.getInstance(), MoveMeta.empty());
    }

    public PathPieces(BasicPiece sourcePiece, List<BasicPiece> waypointPieces, BasicPiece destinationPiece) {
        this(sourcePiece, waypointPieces, destinationPiece, MoveMeta.empty());
    }

    public PathPieces(
            BasicPiece sourcePiece,
            List<BasicPiece> waypointPieces,
            BasicPiece destinationPiece,
            MoveMeta moveMeta
    ) {
        this.sourcePiece = sourcePiece;
        this.waypointPieces = waypointPieces;
        this.destinationPiece = destinationPiece;
        this.moveMeta = moveMeta;
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

    public boolean isSourceInPalace() {
        return moveMeta.sourceInPalace();
    }

    public boolean isDestinationInPalace() {
        return moveMeta.destinationInPalace();
    }

    public boolean isOrthogonalMove() {
        return !moveMeta.diagonalMove();
    }

    public boolean isPalaceMove() {
        return moveMeta.isPalaceMove();
    }
}
