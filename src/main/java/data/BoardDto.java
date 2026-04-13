package data;

import domain.piece.Camp;

public record BoardDto(
        Long id,
        boolean gameInProgress,
        Camp turn
) {
}
