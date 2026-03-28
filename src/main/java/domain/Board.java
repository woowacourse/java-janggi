package domain;

import java.util.Map;

public class Board {
    private final Map<Position, Piece> board;
    public Board(Map<Position, Piece> board) {
        this.board = board;
    }
}
