package domain.piece;

import domain.CountryType;

public record PieceInfo(
        PieceType pieceType,
        CountryType countryType
) {
}
