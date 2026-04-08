package repository;

import domain.pieces.PieceType;
import domain.pieces.Side;

public record SavedPieceDto(
        int row,
        int column,
        Side side,
        PieceType pieceType
) {
}
