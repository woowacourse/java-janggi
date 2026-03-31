package domain.piece;

import domain.BoardStatus;
import domain.piece.strategy.MoveStrategy;
import domain.position.Position;

public class EmptyPiece extends Piece {
    public EmptyPiece(MoveStrategy moveStrategy, Team team) {
        super(moveStrategy, PieceType.PO, team);
    }

    @Override
    public void check(BoardStatus boardStatus, Position start, Position destination) {
        throw new IllegalStateException(PieceErrorMessage.EMPTY_PIECE.getMessage());
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
