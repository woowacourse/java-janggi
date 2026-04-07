package janggigame;

import domain.piece.Side;

public record GameMetaData(
        Long id,
        JanggiGameStatus status,
        Side currentTurn
) {
}
