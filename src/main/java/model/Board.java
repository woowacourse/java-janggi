package model;

import java.util.ArrayList;
import java.util.List;
import model.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<Position, Piece> board;

    public Board() {
        this.board = new HashMap<>();
    }

    private void place(Position position, Piece piece) {
        board.put(position, piece);
    }

    public boolean isPieceAt(Position position, Piece piece) {
        return board.get(position)
                .equals(piece);
    }
}
