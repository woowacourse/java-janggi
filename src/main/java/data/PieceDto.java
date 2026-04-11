package data;

import domain.place.piece.PieceSymbol;
import domain.place.piece.Side;

public record PieceDto(
        Long boardId,
        PieceSymbol pieceSymbol,
        Side side,
        int row,
        int column) {
}
