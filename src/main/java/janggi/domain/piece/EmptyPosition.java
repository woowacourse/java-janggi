package janggi.domain.piece;

import janggi.domain.mouveRule.MoveRule;

public class EmptyPosition extends Piece {

    public EmptyPosition(Team team) {
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
