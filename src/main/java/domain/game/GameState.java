package domain.game;

import domain.coordination.Coordination;
import domain.piece.Piece;
import java.util.Map;

public record GameState(Turn currentTurn, Map<Coordination, Piece> board) {

    public GameState {
        board = Map.copyOf(board);
    }
}
