package core;

import movepolicy.MoveHistory;

public record GameTurnResult(
    JanggiGame updatedGame,
    boolean endedByScore,
    boolean unDo,
    MoveHistory moveHistory
) {

    public static GameTurnResult endByScore(final JanggiGame updatedGame) {
        return new GameTurnResult(updatedGame, true, false, null);
    }

    public static GameTurnResult move(final JanggiGame updatedGame, final MoveHistory moveHistory) {
        return new GameTurnResult(updatedGame, false, false, moveHistory);
    }

    public static GameTurnResult undo(JanggiGame game) {
        return new GameTurnResult(game, false, true, null);
    }

    public boolean hasNoPieceMove() {
        return moveHistory == null;
    }
}
