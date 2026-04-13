package data;

import domain.place.piece.PieceSymbol;
import domain.place.piece.Side;

public record PieceDto(
        Long id,
        Long gameId,
        PieceSymbol pieceSymbol,
        Side side,
        int row,
        int column) {
}
