package view.dto;

import pieces.PieceType;
import pieces.Side;

public record PieceDto(PieceType pieceType, Side side) {
    public boolean isEmpty() {
        return pieceType == PieceType.EMPTY;
    }
}
