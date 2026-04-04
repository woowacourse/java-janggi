package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.exception.business.EmptyPieceException;
import janggi.exception.business.EmptyPositionException;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Board implements BoardState {
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
        if (board.get(position) == null) {
            throw new EmptyPieceException();
        }
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

    public Collection<Piece> getPieces() {
        return Collections.unmodifiableCollection(board.values());
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }
}
