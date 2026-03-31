package dto;

import domain.piece.Piece;
import domain.piece.PieceType;

public record PieceNameDto(String displayName, boolean isCho) {

    public static PieceNameDto from(final Piece piece) {
        PieceType pieceType = piece.getPieceType();
        if (piece.isChoPiece()) {
            return new PieceNameDto(pieceType.getNameForCho(), true);
        }
        return new PieceNameDto(pieceType.getNameForHan(), false);
    }
}
