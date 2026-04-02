package domain.piece;

import domain.Country;

public record PieceInfo(
        PieceType pieceType,
        Country country
) {
}
