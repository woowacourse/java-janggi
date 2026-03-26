package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(BoardDesignPolicy boardDesignPolicy) {
        this.board = boardDesignPolicy.initBoard();
    }

    public Map<Position, Piece> board() {
        return Map.copyOf(board);
    }

}
