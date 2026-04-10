package movepolicy.rule;

import java.util.List;
import pieces.Piece;

public class MoveTrace {

    private final Piece movingPiece;
    private final List<Piece> pathPieces;
    private final Piece targetPiece;

    public MoveTrace(final Piece movingPiece, final List<Piece> pathPieces, final Piece targetPiece) {
        this.movingPiece = movingPiece;
        this.pathPieces = pathPieces;
        this.targetPiece = targetPiece;
    }

    public boolean hasPathPieces() {
        return !pathPieces.isEmpty();
    }

    public boolean isMovingPiecePo() {
        return movingPiece.isPo();
    }

    public boolean hasPathPieceCount(final int pathPiecesCount) {
        return pathPieces.size() == pathPiecesCount;
    }

    public boolean hasPoInPath() {
        return pathPieces.stream()
            .anyMatch(Piece::isPo);
    }

    public boolean isTargetEmpty() {
        return targetPiece == null;
    }

    public boolean isTargetSameSide() {
        if (isTargetEmpty()) {
            return false;
        }
        return targetPiece.isSameSide(movingPiece);
    }

    public boolean isTargetPiecePo() {
        if (isTargetEmpty()) {
            return false;
        }
        return targetPiece.isPo();
    }
}
