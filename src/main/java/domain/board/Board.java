package domain.board;

import domain.coordination.Coordination;
import domain.piece.Piece;
import util.PieceName;

import java.util.Map;

public class Board {

    private final Map<Coordination, Piece> board;

    public Board(Map<Coordination, Piece> board) {
        this.board = board;
    }

    public String find(int col, int row) {
        Piece piece = board.get(Coordination.of(col, row));
        return PieceName.from(piece.getClass().getSimpleName(), piece.team());
    }
}
