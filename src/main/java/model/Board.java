package model;

import model.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> board;

    private Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public static Board init() {
        return new Board(new HashMap<>());
    }

    private void place(Position position, Piece piece) {
        board.put(position, piece);
    }

    public boolean isPieceAt(Position position, Piece piece) {
        return board.get(position)
                .equals(piece);
    }

    public static Board initialize(){
        return null;
    }
}
