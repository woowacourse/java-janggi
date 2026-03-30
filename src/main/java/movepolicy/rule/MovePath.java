package movepolicy.rule;

import java.util.List;
import pieces.FullPiece;
import pieces.Piece;

public class MovePath {

    private final FullPiece movingPiece;
    private final List<Piece> interveningPieces;
    private final Piece targetPiece;

    public MovePath(FullPiece movingPiece, List<Piece> interveningPieces, Piece targetPiece) {
        this.movingPiece = movingPiece;
        this.interveningPieces = interveningPieces;
        this.targetPiece = targetPiece;
    }

    public boolean hasInterveningFullPiece() {
        return interveningPieces.stream()
            .anyMatch(piece -> !piece.isEmpty());
    }

    public List<FullPiece> getInterveningFullPieces() {
        return interveningPieces.stream()
            .filter(piece -> !piece.isEmpty())
            .map(Piece::asFullPiece)
            .toList();
    }

    public boolean isTargetEmpty() {
        return targetPiece.isEmpty();
    }

    public boolean isTargetSameSide() {
        return !isTargetEmpty() && movingPiece.isSameSide(targetPiece.asFullPiece());
    }

    public boolean isMovingPieceIsPo() {
        return movingPiece.isPo();
    }

    public boolean isTargetPiecePo() {
        return !isTargetEmpty() && targetPiece.asFullPiece().isPo();
    }
}
