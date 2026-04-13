package persistence;

import domain.game.Side;
import domain.board.Board;
import domain.board.Formation;

public record SavedGame(
        long id,
        String choPlayerName,
        String hanPlayerName,
        Formation choFormation,
        Formation hanFormation,
        Board board,
        Side currentSide,
        int moveCount
) {
}
