package domain.piece;

import domain.board.Country;

public record PieceInfo(
        PieceType pieceType,
        Country country
) {
}
