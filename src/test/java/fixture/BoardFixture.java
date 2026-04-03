package fixture;

import domain.coordination.Coordination;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.Team;
import java.util.Map;

public class BoardFixture {

    private final Map<Coordination, Piece> board;

    protected BoardFixture(Map<Coordination, Piece> board) {
        this.board = board;
    }

    public BoardFixture moveIgnoringValidation(Coordination from, Coordination to) {
        Piece piece = board.get(from);
        board.put(to, piece);
        board.put(from, new EmptyPiece(Team.NONE));
        return this;
    }

    public BoardFixture place(Coordination at, Piece piece) {
        board.put(at, piece);
        return this;
    }

    public Map<Coordination, Piece> map() {
        return Map.copyOf(board);
    }
}
