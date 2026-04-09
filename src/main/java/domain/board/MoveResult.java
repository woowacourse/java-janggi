package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.Optional;

public record MoveResult(Optional<Piece> capturedPiece) {

    public static MoveResult from(Optional<Piece> capturedPiece) {
        return new MoveResult(capturedPiece);
    }

    public boolean capturedKing() {
        return capturedPiece
                .map(piece -> piece.getPieceType() == PieceType.KING)
                .orElse(false);
    }
}
