package domain.board;

import domain.coordination.Coordination;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.Team;
import util.PieceName;

import java.util.Map;

public class Board {

    protected final Map<Coordination, Piece> board;

    public Board(Map<Coordination, Piece> board) {
        this.board = board;
    }

    public void move(Coordination from, Coordination to) {
        Piece piece = board.get(from);
        piece.validateMovable(from, to, Map.copyOf(board));
        resolve(from, to, piece);
    }

    public String find(int col, int row) {
        Piece piece = board.get(Coordination.of(col, row));
        return PieceName.from(piece.getClass().getSimpleName(), piece.team());
    }

    private void resolve(Coordination from, Coordination to, Piece piece) {
        board.put(to, piece);
        board.put(from, new EmptyPiece(Team.NONE));
    }
}
