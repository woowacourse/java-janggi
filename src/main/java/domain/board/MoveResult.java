package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.Optional;

public class MoveResult {
    private final Optional<Piece> capturedPiece;

    private MoveResult(final Optional<Piece> capturedPiece) {
        this.capturedPiece = capturedPiece;
    }

    public static MoveResult withoutCapture() {
        return new MoveResult(Optional.empty());
    }

    public static MoveResult withCapture(final Piece capturedPiece) {
        return new MoveResult(Optional.of(capturedPiece));
    }


    public boolean capturesGeneral() {
        return capturedPiece
                .map(piece -> piece.isSameType(PieceType.GENERAL))
                .orElse(false);
    }

    public int capturedScore() {
        return capturedPiece
                .map(Piece::getScore)
                .orElse(0);
    }
}
