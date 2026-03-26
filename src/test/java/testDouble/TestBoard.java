package testDouble;

import domain.board.Board;
import domain.coordination.Coordination;
import domain.piece.Piece;

import java.util.Map;

public class TestBoard extends Board {

    public TestBoard(Map<Coordination, Piece> board) {
        super(board);
    }

    public Map<Coordination, Piece> getBoard() {
        return Map.copyOf(board);
    }
}
