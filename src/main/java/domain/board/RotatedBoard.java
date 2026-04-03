package domain.board;

import domain.piece.Piece;
import domain.position.Position;

public class RotatedBoard implements BoardState {
    private final BoardState originBoard;

    public RotatedBoard(BoardState originBoard) {
        this.originBoard = originBoard;
    }

    @Override
    public boolean isBlocked(Position position) {
        return originBoard.isBlocked(Position.rotate180from(position));
    }

    @Override
    public Piece findBy(Position position) {
        return originBoard.findBy(Position.rotate180from(position));
    }
}
