package dto;

import domain.piece.Piece;
import domain.piece.Side;

public record PieceDto(
        String pieceTypeName,
        Side side
) {
    public static PieceDto from(Piece piece) {
        return new PieceDto(piece.getTypeName(), piece.getSide());
    }
}
