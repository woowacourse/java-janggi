package domain.piece;

import domain.country.CountryType;

public record PieceInfo(
        PieceType pieceType,
        CountryType countryType
) {
}
