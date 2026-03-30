package movepolicy.rule;

import java.util.List;
import java.util.Optional;
import pieces.Piece;

public class MoveTrace {

    private final Piece movingPiece;
    private final List<Piece> pathPieces;
    private final Optional<Piece> targetPiece;

    public MoveTrace(Piece movingPiece, List<Piece> pathPieces, Optional<Piece> targetPiece) {
        this.movingPiece = movingPiece;
        this.pathPieces = pathPieces;
        this.targetPiece = targetPiece;
    }

    public boolean hasPathPiece() {
        return !pathPieces.isEmpty();
    }

    public List<Piece> getPathPieces() {
        return pathPieces;
    }

    public boolean isMovingPieceIsPo() {
        return movingPiece.isPo();
    }

    public boolean isTargetEmpty() {
        return targetPiece.isEmpty();
    }

    public boolean isTargetSameSide() {
        return targetPiece
            .map(movingPiece::isSameSide)
            .orElse(false);
    }

    public boolean isTargetPiecePo() {
        return targetPiece
            .map(Piece::isPo)
            .orElse(false);
    }
}
