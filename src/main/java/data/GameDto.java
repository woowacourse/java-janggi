package data;

import domain.place.piece.Side;

public record GameDto(
        Long id,
        String playerCho,
        String playerHan,
        Side currentTurn,
        boolean status
) {
}
