package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;

public class BoardMediatorImpl implements BoardMediator {

    private final Board board;

    public BoardMediatorImpl(final Board board) {
        this.board = board;
    }

    @Override
    public boolean hasPieceAt(final Position position) {
        return !board.isBlank(position);
    }

    @Override
    public Piece getPieceInPosition(final Position position) {
        return board.findPieceByPosition(position);
    }
}
