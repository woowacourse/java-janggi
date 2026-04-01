package janggi.domain;

import janggi.exception.EmptyPositionException;

import java.util.HashMap;
import java.util.Map;

public class Board implements BoardState{
    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> initialPieces) {
        this.board = new HashMap<>(initialPieces);
    }

    @Override
    public boolean hasPieceAt(Position position) {
        return board.containsKey(position);
    }

    @Override
    public Piece getPieceAt(Position position) {
        return board.get(position);
    }

    public void move(Position from, Position to) {
        Piece movingPiece = board.get(from);

        if (movingPiece == null) {
            throw new EmptyPositionException();
        }

        movingPiece.verifyMove(from, to, this);
        board.remove(from);
        board.put(to, movingPiece);
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }
}
