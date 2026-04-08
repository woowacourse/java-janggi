package persistence;

import domain.board.Formation;
import java.util.List;

public record SavedGame(
        long id,
        String choPlayerName,
        String hanPlayerName,
        Formation choFormation,
        Formation hanFormation,
        List<MoveCommand> moves
) {
    public SavedGame {
        moves = List.copyOf(moves);
    }

    public int moveCount() {
        return moves.size();
    }
}
