package domain.dto;

import domain.Country;

public record GameFindDto(int gameId, Country currTurn) {
}
