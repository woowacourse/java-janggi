package janggigame.result;

import janggigame.GameMetaData;

public record LoadGameResult(
        GameMetaData gameMetaData,
        boolean isNewGame
) {
}
