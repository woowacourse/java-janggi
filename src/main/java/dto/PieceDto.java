package dto;

import domain.pieces.PieceType;
import domain.pieces.Side;

public record PieceDto(PieceType pieceType, Side side) {
    public boolean isEmpty() {
        return pieceType == PieceType.EMPTY;
    }
}
