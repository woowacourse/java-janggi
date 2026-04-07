package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.movement.Direction;
import janggi.domain.piece.Piece;

public class BoardMediatorImpl implements BoardMediator {

    private final Board board;

    public BoardMediatorImpl(final Board board) {
        this.board = board;
    }

    @Override
    public boolean canMove(final Position position, final Direction direction) {
        return board.canMove(position, direction);
    }

    @Override
    public boolean existsByPosition(final Position position) {
        return !board.isBlank(position);
    }

    @Override
    public Piece getPieceByPosition(final Position position) {
        return board.findPieceByPosition(position);
    }
}
