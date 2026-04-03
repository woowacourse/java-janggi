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
        throw new UnsupportedOperationException("'빈 공간'은 이동할 수 없습니다.");
    }
}
