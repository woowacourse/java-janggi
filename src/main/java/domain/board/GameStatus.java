package domain.board;

import domain.piece.Country;

public record GameStatus(Country country, int turnCount) {
}
