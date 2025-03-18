package janggi;

import janggi.piece.Piece;
import java.util.Map;

public final class Board {

    private final Map<Position, Piece> board;

    public Board(final Map<Position, Piece> board) {
        this.board = board;
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }
}
