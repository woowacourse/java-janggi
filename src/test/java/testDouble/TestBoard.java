package testDouble;

import domain.board.Board;
import domain.coordination.Coordination;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.Team;

import java.util.Map;

public class TestBoard extends Board {

    public TestBoard(Map<Coordination, Piece> board) {
        super(board);
    }

    public Map<Coordination, Piece> getBoard() {
        return Map.copyOf(board);
    }

    public void move(Coordination from, Coordination to) {
        Piece piece = this.board.get(from);
        this.board.put(to, piece);
        this.board.put(from, new EmptyPiece(Team.NONE));
    }
}
