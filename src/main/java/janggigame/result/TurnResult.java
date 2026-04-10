package janggigame.result;

import domain.piece.Side;

public record TurnResult(
        Side nextTurnSide,
        boolean isJangGun
) {
}
