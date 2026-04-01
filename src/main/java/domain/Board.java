package domain;

import java.util.Map;

public class Board {
    Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }
}
