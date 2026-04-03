package janggi.domain.piece;

import janggi.domain.mouveRule.MoveRule;

public class EmptyPiece extends Piece {

    public EmptyPiece(Team team) {
        super(team);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public PieceType pieceType() {
        return PieceType.EMPTY;
    }

    @Override
    public MoveRule moveRule() {
        return null;
    }
}
