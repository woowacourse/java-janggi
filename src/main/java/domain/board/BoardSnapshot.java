package domain.board;

import domain.country.CountryType;
import domain.piece.PieceInfos;

public record BoardSnapshot(PieceInfos pieceInfos, CountryType countryType) {
}
