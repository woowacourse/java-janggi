package model;

import model.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<Position, Piece> board;

    public Board() {
        this.board = new HashMap<>();
    }

    public void place(Position position, Piece piece) {
        board.put(position, piece);
    }

    public void remove(Position position) {
        board.remove(position);
    }

    public boolean isPieceAt(Position position, Piece piece) {
        return piece.equals(board.get(position));
    }

    public Piece findPiece(Position position) {
        return board.get(position);
    }

    public boolean isPathEmpty(Position position) {
        if (findPiece(position) == null) {
            return true;
        }
        return false;
    }
}
