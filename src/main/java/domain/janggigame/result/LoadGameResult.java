package domain.janggigame.result;

import domain.janggigame.Game;

public record LoadGameResult(
        Game game,
        boolean isNewGame
) {
}
