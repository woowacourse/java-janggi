package service;

import core.JanggiGame;
import core.MoveHistory;

public record GameTurnResult(
    JanggiGame updatedGame,
    boolean undoRequested,
    MoveHistory moveHistory
) {

    public static GameTurnResult endByScore(final JanggiGame updatedGame) {
        return new GameTurnResult(updatedGame, false, null);
    }

    public static GameTurnResult move(final JanggiGame updatedGame, final MoveHistory moveHistory) {
        return new GameTurnResult(updatedGame, false, moveHistory);
    }

    public static GameTurnResult undoRequested(final JanggiGame game) {
        return new GameTurnResult(game, true, null);
    }

    public boolean hasNoPieceMove() {
        return moveHistory == null;
    }
}
