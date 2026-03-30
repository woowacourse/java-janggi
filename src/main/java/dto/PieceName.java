package dto;

import domain.piece.Piece;
import domain.piece.PieceType;

public record PieceName(String displayName, boolean isCho) {

    public static PieceName from(final Piece piece) {
        PieceType pieceType = piece.getPieceType();
        if (piece.isChoPiece()) {
            return new PieceName(pieceType.getNameForCho(), true);
        }
        return new PieceName(pieceType.getNameForHan(), false);
    }
}
