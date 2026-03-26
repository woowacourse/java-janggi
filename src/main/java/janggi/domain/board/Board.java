package janggi.domain.board;

import janggi.domain.piece.Piece;

import java.util.Collections;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }

    public boolean isPresentAt(Position position) {
        return board.containsKey(position);
    }
}
