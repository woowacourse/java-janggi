package core;

import movepolicy.MoveHistory;

public record GameTurnResult(
    JanggiGame updatedGame,
    boolean endedByScore,
    MoveHistory moveHistory
) {

    public static GameTurnResult endByScore(final JanggiGame updatedGame) {
        return new GameTurnResult(updatedGame, true, null);
    }

    public static GameTurnResult move(final JanggiGame updatedGame, final MoveHistory moveHistory) {
        return new GameTurnResult(updatedGame, false, moveHistory);
    }

    public boolean hasNoPieceMove() {
        return moveHistory == null;
    }
}
