package janggigame;

import domain.piece.Side;

public record GameMetaData(
        Long id,
        JanggiGameStatus status,
        Side currentTurn,
        int choJangGunCount,
        int hanJangGunCount
) {
    public static GameMetaData newGame() {
        return new GameMetaData(
                null,
                JanggiGameStatus.WAITING_HAN_PLACEMENT,
                Side.CHO,
                0,
                0
        );
    }
}
